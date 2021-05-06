package com.lab.ali.elasticqueryservice.transformer;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.lab.ali.elastic.model.index.TwitterIndexModel;
import com.lab.ali.elasticqueryservice.api.ElasticDocumentController;
import com.lab.ali.elasticqueryservice.model.ElasticQueryServiceResponseModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ElasticQueryServiceResponseModelAssembler 
   extends RepresentationModelAssemblerSupport<TwitterIndexModel,
   ElasticQueryServiceResponseModel>	{

	public ElasticQueryServiceResponseModelAssembler() {
		super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
	}

	@Override
	public ElasticQueryServiceResponseModel toModel(TwitterIndexModel entity) {
		
		ElasticQueryServiceResponseModel elasticQueryServiceResponseModel
			= new ElasticQueryServiceResponseModel();
		
		elasticQueryServiceResponseModel.setCreatedAt(entity.getCreatedAt());
		elasticQueryServiceResponseModel.setId(entity.getId());
		elasticQueryServiceResponseModel.setText(entity.getText());
		elasticQueryServiceResponseModel.setUserId(entity.getUserId());

		elasticQueryServiceResponseModel.
			add(linkTo(methodOn(ElasticDocumentController.class).getDocumentById(entity.getId())).
					withSelfRel());
		elasticQueryServiceResponseModel.add(
                linkTo(ElasticDocumentController.class)
                        .withRel("documents"));

		
		return elasticQueryServiceResponseModel;
	}

    public List<ElasticQueryServiceResponseModel> toModels(List<TwitterIndexModel> twitterIndexModels) {
    	return twitterIndexModels.stream().map(this::toModel).collect(Collectors.toList());
    }
}
