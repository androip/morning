package morning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import morining.dto.proc.ProcessTemplateDTO;
import morining.dto.proc.edge.EdgeDto;
import morning.entity.process.ProcessTemplate;
import morning.entity.process.edge.Edge;

@Component
public class ProcessTemplateDtoFactory {

	public ProcessTemplateDTO createDto(ProcessTemplate entity) {
		ProcessTemplateDTO dto = new ProcessTemplateDTO();
		dto.setProcessName(entity.getProcessName());
		dto.setProcessTemplateId(entity.getProcessTemplateId());
		dto.setEdgeDtoList(transfromEdgeToDto(entity.getEdgeList()));
		return dto;
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

}
