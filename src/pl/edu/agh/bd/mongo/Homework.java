package pl.edu.agh.bd.mongo;

import com.mongodb.*;

import java.util.Arrays;

public class Homework {
    private DBCollection collection;

    public Homework() {
        this.collection = new MongoLab().getCollection();
    }

    private void displayCursorResults(Cursor cursor){
        int i = 0;

        try {
            while (cursor.hasNext()){
                System.out.println(cursor.next());
                i++;
            }
        } finally {
            cursor.close();
            System.out.println("We have " + i + " results.\n");
        }
    }

    public void simpleQuery(){
        BasicDBObject query = new BasicDBObject("$and", Arrays.asList(
                new BasicDBObject("category", "HISTORY"),
                new BasicDBObject("air_date",
                        new BasicDBObject("$gt", "2000-12-31"))));

        BasicDBObject sort = new BasicDBObject("show_number", 1);
        DBCursor cursor = collection.find(query).sort(sort);

        displayCursorResults(cursor);
    }

    public void aggregateQuery(){
        BasicDBObject match = new BasicDBObject("$match",
                new BasicDBObject("$or", Arrays.asList(
                        new BasicDBObject("show_number",
                                new BasicDBObject("$gt", "4000")),
                        new BasicDBObject("value", "$1,000"))));

        BasicDBObject groupFields = new BasicDBObject("_id", "$category");
        groupFields.put("total",
                new BasicDBObject("$sum", 1));

        BasicDBObject group = new BasicDBObject("$group", groupFields);

        BasicDBObject sort = new BasicDBObject("$sort",
                new BasicDBObject("total", -1));

        BasicDBObject limit = new BasicDBObject("$limit", 5);

        AggregationOptions options= AggregationOptions.builder().
                outputMode(AggregationOptions.OutputMode.CURSOR).build();

        Cursor aggregation = collection.aggregate(Arrays.asList(match, group, sort, limit), options);

        displayCursorResults(aggregation);
    }

    private void displayMapReduceResult(MapReduceOutput output){
        output.results().forEach(x -> System.out.println(x.toString()));

    }

    public void mapReduceQuery(){
        String map = "function(){emit(this.category, { count: 1, round: this.round, " +
                "number: this.show_number, date: this.air_date});};";

        String reduce = "function (categories, values){ var value = {count: 0, round: " +
                "\"\", number: 0, avg: 0}; var date = new Date('2010-12-31'); " +
                "for(var i = 0; i < values.length; i++){ if (date.getTime() > " +
                "new Date(values[i].date).getTime()){value.count += values[i].count; " +
                "value.number += parseInt(values[i].number);} value.round += values[i].round;} " +
                "if(value.count !== 0) value.avg = value.number / value.count; else " +
                "value.avg = 'Records after 2010 year'; return value;};";

        MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, null,
                MapReduceCommand.OutputType.INLINE, null);

        MapReduceOutput output = collection.mapReduce(cmd);

        displayMapReduceResult(output);
    }
}
