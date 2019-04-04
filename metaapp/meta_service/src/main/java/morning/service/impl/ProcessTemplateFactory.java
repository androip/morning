package morning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import morining.dto.proc.ProcessTemplateDTO;
import morining.dto.proc.edge.EdgeDto;
import morining.dto.proc.node.ConditionDto;
import morining.dto.proc.node.GatewayNodeTemplateDto;
import morining.dto.proc.node.NodeTemplateDto;
import morining.dto.proc.node.form.FormPropertyDto;
import morining.dto.proc.node.form.FormStructureDto;
import morining.dto.proc.node.form.field.FiedPropertyDto;
import morining.dto.proc.node.form.field.RelationInfoDto;
import morning.entity.process.ProcessTemplate;
import morning.entity.process.edge.Edge;
import morning.entity.process.node.Condition;
import morning.entity.process.node.GatewayNodeTemplate;
import morning.entity.process.node.NodeTemplate;
import morning.entity.process.node.form.FORMTYPE;
import morning.entity.process.node.form.FormProperty;
import morning.entity.process.node.form.FormStructure;
import morning.entity.process.node.form.filed.FieldProperty;
import morning.entity.process.node.form.filed.RelationInfo;

@Component
public class ProcessTemplateFactory {

	public ProcessTemplate create(ProcessTemplateDTO dto) {
		
		ProcessTemplate entity = new ProcessTemplate();
		entity.setProcessTemplateId(dto.getProcessTemplateId());
		entity.setProcessName(dto.getProcessName());
		List<NodeTemplate> nodeTemplateList = transformNodeTemplate(dto.getNodeTemplateDtoList());
		entity.setNodeTemplateList(nodeTemplateList);
		entity.setGatewayNodeTemplateList(transformGateway(dto.getGatewayNodeTemplateDtoList()));
		List<Edge> edgeList = transformEdges(dto.getEdgeDtoList());
		entity.setEdgeList(edgeList);
		
		return entity;
	}

	private List<GatewayNodeTemplate> transformGateway(List<GatewayNodeTemplateDto> gatewayNodeTemplateDtoList) {
		List<GatewayNodeTemplate> gatewayList = new ArrayList<GatewayNodeTemplate>();
		for(GatewayNodeTemplateDto dto:gatewayNodeTemplateDtoList) {
			GatewayNodeTemplate gateway = new GatewayNodeTemplate();
			gateway.setNodeTemplateId(dto.getNodeTemplateId());
			gateway.setNodeTemplateName(dto.getNodeTemplateName());
			gateway.setNodeTemplateType(dto.getNodeTemplateType());
			gateway.setFromStructure(transformStructure(dto.getFromStructure()));
			gateway.setFormProeties(transformFormProeties(dto.getFormProperties()));
			gateway.setCondition(transformCondition(dto.getCondition()));
			gateway.setDefaultEdgeIds(dto.getDefaultEdgeId());
			gatewayList.add(gateway);
		}
		return gatewayList;
	}

	private Condition transformCondition(ConditionDto dto) {
		
		return new Condition(dto.getFiledKey(),dto.getTargets());
	}

	private List<Edge> transformEdges(List<EdgeDto> edgeDtoList) {
		List<Edge> edges = new ArrayList<Edge>() {/**
			 * 
			 */
			private  final long serialVersionUID = -6619510969308132611L;

		{
			edgeDtoList.forEach(dto->{
				add(new Edge(dto.getId(),dto.getFrom(),dto.getTo()));
			});
			
		}};
		return edges;
	}

	private List<NodeTemplate> transformNodeTemplate(List<NodeTemplateDto> nodeTemplateDtoList) {
		List<NodeTemplate> nodeTemplateList = new ArrayList<NodeTemplate>() {/**
			 * 
			 */
			private  final long serialVersionUID = 1L;

		{
			nodeTemplateDtoList.forEach(dto->{
				NodeTemplate nodeTmpl = new NodeTemplate();
				nodeTmpl.setNodeTemplateId(dto.getNodeTemplateId());
				nodeTmpl.setNodeTemplateName(dto.getNodeTemplateName());
				nodeTmpl.setNodeTemplateType(dto.getNodeTemplateType());
				nodeTmpl.setFormProeties(transformFormProeties(dto.getFormProperties()));
				nodeTmpl.setFromStructure(transformStructure(dto.getFromStructure()));
				add(nodeTmpl);
			});
		}};
		
		return nodeTemplateList;
	}

	private FormStructure transformStructure(FormStructureDto dto) {
		return new FormStructure(dto.getParentId(),dto.getSelfId());
	}

	private  List<FormProperty> transformFormProeties(List<FormPropertyDto> list) {
		List<FormProperty> proerties = new ArrayList<FormProperty>();
		for(FormPropertyDto dto:list) {
			FormProperty property = new FormProperty();
			property.setFormTemplateId(dto.getFormTemplateId());
			property.setFormName(dto.getFormName());
			property.setFormType(FORMTYPE.getEnumByTypeValue(dto.getFormType()));
			property.setFiledProperties(transformFieldPreperties(dto.getFieldProperties()));
			proerties.add(property);
		}
		return proerties;
	}


	private  List<FieldProperty> transformFieldPreperties(List<FiedPropertyDto> filedProperties) {
		return new ArrayList<FieldProperty>() {/**
			 * 
			 */
			private  final long serialVersionUID = 1L;

		{
			filedProperties.forEach(dto->{
				add(new FieldProperty(dto.getFiledKey(),
						dto.getFiledName(),
						dto.getFiledViewName(),
						dto.getFiledType(),
						dto.getReadOnly(),
						dto.getVisible(),
						transformrelationInfo(dto.getRelation())));
			});
		}

		private RelationInfo transformrelationInfo(RelationInfoDto dto) {
			
			return new RelationInfo(dto.getRelationSource(),
					dto.getViewFkey(),
					dto.getForeignFkey(),
					dto.getConditionFKey());
		}};
	}


}
