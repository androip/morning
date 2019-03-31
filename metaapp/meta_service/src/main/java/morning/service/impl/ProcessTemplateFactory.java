package morning.service.impl;

import java.util.ArrayList;
import java.util.List;

import morining.dto.proc.ProcessTemplateDTO;
import morining.dto.proc.node.NodeTemplateDto;
import morining.dto.proc.node.form.FormPropertyDto;
import morining.dto.proc.node.form.FormStructureDto;
import morining.dto.proc.node.form.field.FiedPropertyDto;
import morining.dto.proc.node.form.field.RelationInfoDto;
import morning.entity.process.ProcessTemplate;
import morning.entity.process.node.NodeTemplate;
import morning.entity.process.node.form.FORMTYPE;
import morning.entity.process.node.form.FormProperty;
import morning.entity.process.node.form.FormStructure;
import morning.entity.process.node.form.filed.FieldProperty;
import morning.entity.process.node.form.filed.RelationInfo;

public class ProcessTemplateFactory {

	public static ProcessTemplate create(ProcessTemplateDTO dto) {
		
		ProcessTemplate entity = new ProcessTemplate();
		entity.setProcessTemplateId(dto.getProcessTemplateId());
		entity.setProcessName(dto.getProcessName());
		List<NodeTemplate> nodeTemplateList = transformNodeTemplate(dto.getNodeTemplateDtoList());
		entity.setNodeTemplateList(nodeTemplateList);
		
		return entity;
	}

	private static List<NodeTemplate> transformNodeTemplate(List<NodeTemplateDto> nodeTemplateDtoList) {
		List<NodeTemplate> nodeTemplateList = new ArrayList<NodeTemplate>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			nodeTemplateDtoList.forEach(dto->{
				NodeTemplate nodeTmpl = new NodeTemplate();
				nodeTmpl.setNodeTemplateId(dto.getNodeTemplateId());
				nodeTmpl.setNodeTemplateName(dto.getNodeTemplateName());
				nodeTmpl.setNodeTemplateType(dto.getNodeTemplateType());
				nodeTmpl.setFormProeties(transformFormProeties(dto.getFormProeties()));
				nodeTmpl.setFromStructure(transformStructure(dto.getFromStructure()));
				add(nodeTmpl);
			});
		}};
		
		return nodeTemplateList;
	}

	private static FormStructure transformStructure(FormStructureDto dto) {
		return new FormStructure(dto.getParentId(),dto.getSelfId());
	}

	private static FormProperty transformFormProeties(FormPropertyDto dto) {
		FormProperty property = new FormProperty();
		property.setFormTemplateId(dto.getFormTemplateId());
		property.setFormName(dto.getFormName());
		property.setFormType(FORMTYPE.valueOf(dto.getFormType()));
		property.setFiledProperties(transformFieldPreperties(dto.getFieldProperties()));
		return null;
	}


	private static List<FieldProperty> transformFieldPreperties(List<FiedPropertyDto> filedProperties) {
		return new ArrayList<FieldProperty>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
