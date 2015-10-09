/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.service;

import com.infinity.dto.School;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author t311372
 */
@Service
public class SchoolService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolService.class);

    @Autowired
    private ElasticClientConf elasticClientConf;
    private TransportClient client;

    public ArrayList<School> getByIdSearhText(String id) throws IOException {

        LOG.debug("id du candidat {} ", id);
        client = elasticClientConf.getClient();
//        QueryBuilder qb = QueryBuilders.queryStringQuery(id);
        QueryBuilder qb = QueryBuilders.matchQuery("partialCandidat.id", id);
        SearchResponse response = client.prepareSearch(elasticClientConf.getINDEX_NAME())
                .setTypes("school")
                .setQuery(qb) // Query
                .execute()
                .actionGet();

        SearchHit[] hits = response.getHits().getHits();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<School> candidateSchool = new ArrayList<>();

        if (hits.length > 0) {
            for (int i = 0; i < hits.length; i++) {
                School readValue = mapper.readValue(hits[i].getSourceAsString(), School.class);
                readValue.setId(hits[i].getId());
                candidateSchool.add(readValue);

            }
        }
        return candidateSchool;

    }

    /**
     *
     * @param id
     * @return
     * @throws java.io.IOException
     */
    public School getById(String id) {

        client = elasticClientConf.getClient();
        GetResponse response = client.
                prepareGet(elasticClientConf.getINDEX_NAME(), "school", id)
                .execute()
                .actionGet();

        ObjectMapper mapper = new ObjectMapper();
        School readValue = null;
        try {

            readValue = mapper.readValue(response.getSourceAsString(), School.class);
            readValue.setId(id);
        } catch (IOException e) {

            LOG.error(e.getMessage());
        }

        return readValue;

    }

    public long updateOneById(School school) throws IOException, InterruptedException, ExecutionException {

        client = elasticClientConf.getClient();
        ObjectMapper mapper = new ObjectMapper();

        byte[] json = mapper.writeValueAsBytes(school);

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(elasticClientConf.getINDEX_NAME());
        updateRequest.type("school");
        updateRequest.id(school.getId());
        updateRequest.doc(json);

        UpdateResponse get = client.update(updateRequest).get();
        long version = get.getVersion();
        client.admin().indices().prepareRefresh().execute().actionGet();

        return version;
    }

    public long addSchool(School school) throws JsonProcessingException {

        client = elasticClientConf.getClient();

        ObjectMapper mapper = new ObjectMapper();

        byte[] json = mapper.writeValueAsBytes(school);

        IndexResponse response = client.prepareIndex(elasticClientConf.getINDEX_NAME(), "school")
                .setSource(json)
                .execute()
                .actionGet();

        long version = response.getVersion();
        client.admin().indices().prepareRefresh().execute().actionGet();
        return version;
    }
}
