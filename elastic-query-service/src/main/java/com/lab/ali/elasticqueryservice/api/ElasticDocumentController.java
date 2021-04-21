package com.lab.ali.elasticqueryservice.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lab.ali.elastic.model.index.TwitterIndexModel;
import com.lab.ali.elasticindexclient.service.ElasticQueryClient;
import com.lab.ali.elasticqueryservice.model.ElasticQueryServiceRequestModel;
import com.lab.ali.elasticqueryservice.model.ElasticQueryServiceResponseModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/documents")
@Slf4j
public class ElasticDocumentController {

	ElasticQueryClient<TwitterIndexModel> elasticQueryClient;
	
	public ElasticDocumentController(ElasticQueryClient<TwitterIndexModel> elasticQueryClient) {
		this.elasticQueryClient = elasticQueryClient;
	}
	
    @GetMapping("/")
    public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>>
    	getAllDocuments() {
    	return this.elasticQueryClient.
    			findAllIndexModels().
    			map(models -> models.stream().map(model -> {
    				ElasticQueryServiceResponseModel elasticQueryServiceResponseModel =
    						new ElasticQueryServiceResponseModel();
    				
    				elasticQueryServiceResponseModel.setCreatedAt(model.getCreatedAt());
    				elasticQueryServiceResponseModel.setId(model.getId());
    				elasticQueryServiceResponseModel.setText(model.getText());
    				elasticQueryServiceResponseModel.setUserId(model.getUserId());
    				
    				return elasticQueryServiceResponseModel;
    				}).collect(Collectors.toList())).
    			map(responsemodels -> ResponseEntity.ok(responsemodels)).
    			orElse(ResponseEntity.ok(new ArrayList<>())); 
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<ResponseEntity<ElasticQueryServiceResponseModel>> 
    		getDocumentById(@PathVariable @NotEmpty String id) {
    	
    	return this.elasticQueryClient.getIndexModelById(id)
    		.map(x -> {
				ElasticQueryServiceResponseModel elasticQueryServiceResponseModel =
						new ElasticQueryServiceResponseModel();

				elasticQueryServiceResponseModel.setCreatedAt(x.getCreatedAt());
				elasticQueryServiceResponseModel.setId(x.getId());
				elasticQueryServiceResponseModel.setText(x.getText());
				elasticQueryServiceResponseModel.setUserId(x.getUserId());
				
				return elasticQueryServiceResponseModel;
    		})
    		.map(x -> ResponseEntity.ok(x))
    		;
    }

    @PostMapping("/get-document-by-text")
    public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>>
    getDocumentByText(@RequestBody @Valid ElasticQueryServiceRequestModel elasticQueryServiceRequestModel) {

    	return this.elasticQueryClient.
    			findIndexModelByText(elasticQueryServiceRequestModel.getText()).
    			map(models -> models.stream().map(model -> {
    				ElasticQueryServiceResponseModel elasticQueryServiceResponseModel =
    						new ElasticQueryServiceResponseModel();
    				
    				elasticQueryServiceResponseModel.setCreatedAt(model.getCreatedAt());
    				elasticQueryServiceResponseModel.setId(model.getId());
    				elasticQueryServiceResponseModel.setText(model.getText());
    				elasticQueryServiceResponseModel.setUserId(model.getUserId());
    				
    				return elasticQueryServiceResponseModel;
    				}).collect(Collectors.toList())).
    			map(responsemodels -> ResponseEntity.ok(responsemodels)).
    			orElse(ResponseEntity.ok(new ArrayList<>())); 
    	
    }

}