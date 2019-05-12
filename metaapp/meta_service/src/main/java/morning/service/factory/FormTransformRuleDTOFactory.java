package morning.service.factory;

import morining.dto.rule.FieldTransformRuleDto;
import morining.dto.rule.FormTransformRuleDto;
import morning.bill.FieldTransformRule;
import morning.bill.FormTransformRule;

public class FormTransformRuleDTOFactory {

	public FormTransformRuleDto createDto(FormTransformRule ent) {
		FormTransformRuleDto dto = new FormTransformRuleDto(ent.getTransformId(),
				ent.getProcessTid(),
				ent.getSrcFormTid(),
				ent.getDesFormTId(),
				transformDto(ent.getFieldRule()));
		return dto;
	}

	private FieldTransformRuleDto transformDto(FieldTransformRule fieldRule) {
		FieldTransformRuleDto dto = new FieldTransformRuleDto(fieldRule.getSrcFkey(),
				fieldRule.getDesFkey(),
				fieldRule.getFormula(),
				fieldRule.getRuleType());
		return dto;
	}

}
