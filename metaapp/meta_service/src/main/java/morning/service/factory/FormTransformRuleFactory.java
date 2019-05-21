package morning.service.factory;

import java.util.ArrayList;
import java.util.List;

import morining.dto.rule.FieldTransformRuleDto;
import morining.dto.rule.FormTransformRuleDto;
import morning.bill.FieldTransformRule;
import morning.bill.FormTransformRule;
import morning.util.IdGenerator;

public class FormTransformRuleFactory {

	public FormTransformRule create(FormTransformRuleDto dto) {
		
		FormTransformRule rule = new FormTransformRule(IdGenerator.generatRuleId(),
				dto.getProcessTid(),
				dto.getSrcFormTid(),
				dto.getDesFormTId(),
				transformFieldRuleFromDto(dto.getFieldRule()));
		
		return rule;
	}

	private List<FieldTransformRule> transformFieldRuleFromDto(List<FieldTransformRuleDto> fieldRule) {
		return new ArrayList<FieldTransformRule>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7167481987275672819L;

		{
			fieldRule.forEach(dto->{
				FieldTransformRule fieldRule = new FieldTransformRule(dto.getSrcFkey(),
						dto.getDesFkey(),
						dto.getFormula(),
						dto.getRuleType());
				add(fieldRule);
			});
		}};
	}

	private FieldTransformRule transformFieldRuleFromDto(FieldTransformRuleDto dto) {
		FieldTransformRule fieldRule = new FieldTransformRule(dto.getSrcFkey(),
				dto.getDesFkey(),
				dto.getFormula(),
				dto.getRuleType());
		return fieldRule;
	}

}








