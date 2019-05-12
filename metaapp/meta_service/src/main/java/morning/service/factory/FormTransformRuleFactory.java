package morning.service.factory;

import morining.dto.rule.FieldTransformRuleDto;
import morining.dto.rule.FormTransformRuleDto;
import morning.bill.FieldTransformRule;
import morning.bill.FormTransformRule;

public class FormTransformRuleFactory {

	public FormTransformRule create(FormTransformRuleDto dto) {
		
		FormTransformRule rule = new FormTransformRule(dto.getTransformId(),
				dto.getProcessTid(),
				dto.getSrcFormTid(),
				dto.getDesFormTId(),
				transformFieldRuleFromDto(dto.getFieldRule()));
		
		return rule;
	}

	private FieldTransformRule transformFieldRuleFromDto(FieldTransformRuleDto dto) {
		FieldTransformRule fieldRule = new FieldTransformRule(dto.getSrcFkey(),
				dto.getDesFkey(),
				dto.getFormula(),
				dto.getRuleType());
		return fieldRule;
	}

}







