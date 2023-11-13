package com.pl_project.pl_project.payload;

import java.util.List;

//import org.springframework.data.util.Pair;

public class HistoryResponse {
	

	public HistoryResponse() {
		super();
		this.response_msg="you have no past conversions";
	}
	public HistoryResponse(List<List<String>> list, String response_msg) {
		super();
		this.list = list;
		this.response_msg = response_msg;
	}
	@Override
	public String toString() {
		return "HistoryResponse [list=" + list + ", response_msg=" + response_msg + "]";
	}
	List<List<String>>list;
	String response_msg;
	public List<List<String>> getList() {
		return list;
	}
	public void setList(List<List<String>> list) {
		this.list = list;
	}
	public String getResponse_msg() {
		return response_msg;
	}
	public void setResponse_msg(String response_msg) {
		this.response_msg = response_msg;
	}
}

