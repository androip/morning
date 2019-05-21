package morning.service.factory;

import java.util.ArrayList;
import java.util.List;

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
				transformDto(ent.getFieldRules()));
		return dto;
	}

	private List<FieldTransformRuleDto> transformDto(List<FieldTransformRule> fieldRules) {
		List<FieldTransformRuleDto> dtolist = new ArrayList<FieldTransformRuleDto>();
		for(FieldTransformRule fieldRule:fieldRules) {
			FieldTransformRuleDto dto = new FieldTransformRuleDto(fieldRule.getSrcFkey(),
					fieldRule.getDesFkey(),
					fieldRule.getFormula(),
					fieldRule.getRuleType());
			dtolist.add(dto);
		}
		return dtolist;
	}

}
