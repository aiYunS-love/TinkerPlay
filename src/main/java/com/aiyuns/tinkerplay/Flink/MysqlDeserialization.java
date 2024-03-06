package com.aiyuns.tinkerplay.Flink;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import io.debezium.data.Envelope;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: aiYunS
 * @Date: 2023年4月27日, 0027 下午 7:07:49
 * @Description:  mysql消息读取自定义序列化
 */

public class MysqlDeserialization implements DebeziumDeserializationSchema<DataChangeInfo> {

    public static final String TS_MS = "ts_ms";
    public static final String BIN_FILE = "file";
    public static final String POS = "pos";
    public static final String CREATE = "CREATE";
    public static final String BEFORE = "before";
    public static final String AFTER = "after";
    public static final String SOURCE = "source";
    public static final String UPDATE = "UPDATE";

    /**
     * 获取操作类型 READ CREATE UPDATE DELETE TRUNCATE;
     * 变更类型： 0 初始化 1新增 2修改 3删除 4导致源中的现有表被截断的操作
     */
    private static final Map<String, Integer> OPERATION_MAP = ImmutableMap.of(
            "READ", 0,
            "CREATE", 1,
            "UPDATE", 2,
            "DELETE", 3,
            "TRUNCATE", 4);

    // 反序列化数据,转为变更JSON对象
//    @Override
//    public void deserialize(SourceRecord record, Collector<DataChangeInfo> out) throws Exception {
//        String topic = record.topic();
//        String[] fields = topic.split("\\.");
//        String database = fields[1];
//        String tableName = fields[2];
//        Struct struct = (Struct) record.value();
//        final Struct source = struct.getStruct(SOURCE);
//        DataChangeInfo dataChangeInfo = new DataChangeInfo();
//        dataChangeInfo.setBeforeData( getJsonObject(struct, BEFORE).toJSONString());
//        dataChangeInfo.setAfterData(getJsonObject(struct, AFTER).toJSONString());
//        //5.获取操作类型  CREATE UPDATE DELETE
//        Envelope.Operation operation = Envelope.operationFor(record);
//        String type = operation.toString().toUpperCase();
//        int eventType = type.equals(CREATE) ? 1 : UPDATE.equals(type) ? 2 : 3;
//        dataChangeInfo.setEventType(eventType);
//        dataChangeInfo.setFileName(Optional.ofNullable(source.get(BIN_FILE)).map(Object::toString).orElse(""));
//        dataChangeInfo.setFilePos(Optional.ofNullable(source.get(POS)).map(x->Integer.parseInt(x.toString())).orElse(0));
//        dataChangeInfo.setDatabase(database);
//        dataChangeInfo.setTableName(tableName);
//        dataChangeInfo.setChangeTime(Optional.ofNullable(struct.get(TS_MS)).map(x -> Long.parseLong(x.toString())).orElseGet(System::currentTimeMillis));
//        //7.输出数据
//        out.collect(dataChangeInfo);
//    }

    // 反序列化数据,转为变更JSON对象
    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<DataChangeInfo> collector) throws Exception {
        String topic = sourceRecord.topic();
        String[] fields = topic.split("\\.");
        String database = fields[1];
        String tableName = fields[2];
        Struct struct = (Struct) sourceRecord.value();
        final Struct source = struct.getStruct(SOURCE);
        DataChangeInfo dataChangeInfo = new DataChangeInfo();
        dataChangeInfo.setBeforeData( getJsonObject(struct, BEFORE).toJSONString());
        dataChangeInfo.setAfterData(getJsonObject(struct, AFTER).toJSONString());
        //5.获取操作类型  CREATE UPDATE DELETE
        Envelope.Operation operation = Envelope.operationFor(sourceRecord);
        String type = operation.toString().toUpperCase();
        int eventType = OPERATION_MAP.get(type);
        if (eventType == 3) {
            dataChangeInfo.setData(getJsonObject(struct, BEFORE).toJSONString());
        } else {
            dataChangeInfo.setData(getJsonObject(struct, AFTER).toJSONString());
        }
        dataChangeInfo.setEventType(eventType);
        dataChangeInfo.setFileName(Optional.ofNullable(source.get(BIN_FILE)).map(Object::toString).orElse(""));
        dataChangeInfo.setFilePos(Optional.ofNullable(source.get(POS)).map(x->Integer.parseInt(x.toString())).orElse(0));
        dataChangeInfo.setDatabase(database);
        dataChangeInfo.setTableName(tableName);
        dataChangeInfo.setChangeTime(Optional.ofNullable(struct.get(TS_MS)).map(x -> Long.parseLong(x.toString())).orElseGet(System::currentTimeMillis));
        //7.输出数据
        collector.collect(dataChangeInfo);
    }

    @Override
    public TypeInformation<DataChangeInfo> getProducedType() {
        return TypeInformation.of(DataChangeInfo.class);
    }

    /**
     * 从原数据获取出变更之前或之后的数据
     */
    private JSONObject getJsonObject(Struct value, String fieldElement) {
        Struct element = value.getStruct(fieldElement);
        JSONObject jsonObject = new JSONObject();
        if (element != null) {
            Schema afterSchema = element.schema();
            List<Field> fieldList = afterSchema.fields();
            for (Field field : fieldList) {
                Object afterValue = element.get(field);
                jsonObject.put(field.name(), afterValue);
            }
        }
        return jsonObject;
    }

}
