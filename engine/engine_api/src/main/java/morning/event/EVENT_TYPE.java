package morning.event;

public enum EVENT_TYPE {

	proc_start("proc_start"),
	node_start("node_start"),
	node_end("node_end"),
	proc_end("proc_end");
	
	private String masssge;
	
	EVENT_TYPE(String masssge) {
		this.masssge = masssge;
	}
}
