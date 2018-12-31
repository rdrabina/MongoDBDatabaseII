package pl.edu.agh.bd.mongo;


import com.mongodb.*;

import java.net.UnknownHostException;

public class MongoLab {
	private MongoClient mongoClient;
	private DB db;

	public MongoLab() {
		try {
			mongoClient = new MongoClient();
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}
		db = mongoClient.getDB("jeopardy");
	}

	public DBCollection getCollection(){
		return db.getCollection("question");
	}
}
