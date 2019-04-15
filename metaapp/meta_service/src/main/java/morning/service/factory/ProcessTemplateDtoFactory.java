package morning.service.factory;

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
import morning.entity.process.node.form.FormProperty;
import morning.entity.process.node.form.FormStructure;
import morning.entity.process.node.form.filed.FieldProperty;
import morning.entity.process.node.form.filed.RelationInfo;

@Component
public class ProcessTemplateDtoFactory {

	public ProcessTemplateDTO createDto(ProcessTemplate entity) {
		ProcessTemplateDTO dto = new ProcessTemplateDTO();
		dto.setProcessName(entity.getProcessName());
		dto.setProcessTemplateId(entity.getProcessTemplateId());
		dto.setEdgeDtoList(transfromEdgeToDto(entity.getEdgeList()));
		dto.setNodeTemplateDtoList(transformNodeTemplate(entity.getNodeTemplateList()));
		dto.setGatewayNodeTemplateDtoList(transformGateway(entity.getGatewayNodeTemplateList()));
		dto.setCreateTime(entity.getCreateTime());
		dto.setUpdateTime(entity.getUpdateTime());
		return dto;
	}

	private List<NodeTemplateDto> transformNodeTemplate(List<NodeTemplate> nodeTemplateList) {
		List<NodeTemplateDto> dtos = new ArrayList<NodeTemplateDto>();
		for(NodeTemplate nodetmp : nodeTemplateList) {
			NodeTemplateDto dto = new NodeTemplateDto();
			dto.setNodeTemplateId(nodetmp.getNodeTemplateId());
			dto.setNodeTemplateName(nodetmp.getNodeTemplateName());
			dto.setNodeTemplateType(nodetmp.getNodeTemplateType());
			dto.setFromStructure(transfromFormStructure(nodetmp.getFromStructure()));
			dto.setFormProperties(transformProerties(nodetmp.getFormProeties()));
			dtos.add(dto);
		}
		
		return dtos;
	}

	private List<FormPropertyDto> transformProerties(List<FormProperty> formProeties) {
		 List<FormPropertyDto> retDtos = new ArrayList<FormPropertyDto>();
		 for(FormProperty property:formProeties) {
			 FormPropertyDto dto = new FormPropertyDto();
			 dto.setFormTemplateId(property.getFormTemplateId());
			 dto.setFormName(property.getFormName());
			 dto.setFormType(property.getFormType().getTypeValue());
			 dto.setFieldProperties(transformFieldProperties(property.getFiledProperties()));
			 retDtos.add(dto);
		 }
		return retDtos;
	}

	private List<FiedPropertyDto> transformFieldProperties(List<FieldProperty> filedProperties) {
		List<FiedPropertyDto> dtolist = new ArrayList<FiedPropertyDto>();
		for(FieldProperty fldpty:filedProperties) {
			FiedPropertyDto dto = new FiedPropertyDto();
			dto.setFiledKey(fldpty.getFiledKey());
			dto.setFiledName(fldpty.getFiledName());
			dto.setFiledType(fldpty.getFiledType());
			dto.setFiledViewName(fldpty.getFiledViewName());
			dto.setReadOnly(fldpty.getReadOnly());
			dto.setVisible(fldpty.getVisible());
			dto.setRelation(transfromRelation(fldpty.getRelation()));
			dtolist.add(dto);
		}
		return dtolist;
	}

	private RelationInfoDto transfromRelation(RelationInfo relation) {
		return new RelationInfoDto(relation.getRelationSource(),relation.getViewFkey(),relation.getConditions(),relation.getPkey());
	}

	private FormStructureDto transfromFormStructure(FormStructure fromStructure) {
		return new FormStructureDto(fromStructure.getParentId(),fromStructure.getSelfId());
	}

	private List<GatewayNodeTemplateDto> transformGateway(List<GatewayNodeTemplate> gatewayNodeTemplateList) {
		List<GatewayNodeTemplateDto> gatewalDtolist = new ArrayList<GatewayNodeTemplateDto>();
		for(GatewayNodeTemplate gatetmpl :gatewayNodeTemplateList) {
			GatewayNodeTemplateDto dto = new GatewayNodeTemplateDto();
			dto.setNodeTemplateId(gatetmpl.getNodeTemplateId());
			dto.setNodeTemplateName(gatetmpl.getNodeTemplateName());
			dto.setNodeTemplateType(gatetmpl.getNodeTemplateType());
			dto.setFromStructure(transfromFormStructure(gatetmpl.getFromStructure()));
			dto.setFormProperties(transformProerties(gatetmpl.getFormProeties()));
			dto.setDefaultEdgeId(gatetmpl.getDefaultEdgeIds());
			dto.setCondition(transformCondition(gatetmpl.getCondition()));
			gatewalDtolist.add(dto);
		}
		
		return gatewalDtolist;
	}


	private ConditionDto transformCondition(Condition condition) {
		return new ConditionDto(condition.getFiledKey(),condition.getTargets());
	}

	private List<EdgeDto> transfromEdgeToDto(List<Edge> edgeList) {
		return new ArrayList<EdgeDto>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8118567638127888955L;

		{
			edgeList.forEach(edge->{
				add(new EdgeDto(edge.getId(),edge.getFrom(),edge.getTo()));
			});
		}};
	}

	public List<ProcessTemplateDTO> createDtoList(List<ProcessTemplate> objList) {
		List<ProcessTemplateDTO> list = new ArrayList<ProcessTemplateDTO>();
		for(ProcessTemplate obj : objList) {
			ProcessTemplateDTO dto = createDto(obj);
			list.add(dto);
		}
		return list;
	}

}
