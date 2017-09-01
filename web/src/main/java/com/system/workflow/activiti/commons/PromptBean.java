package com.system.workflow.activiti.commons;

import java.util.HashMap;
import java.util.Map;

public class PromptBean {

	private boolean result;
	
	private String promptMsg;

	public PromptBean(){
		this.promptMsg = "操作成功";
		this.result = true;
	}
	
	public PromptBean(boolean result,String msg){
		this.promptMsg = msg;
		this.result = result;
	}
	
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getPromptMsg() {
		return promptMsg;
	}

	public void setPromptMsg(String promptMsg) {
		this.promptMsg = promptMsg;
	}
	
	public void setResult(boolean result,String msg){
		this.result = result;
		this.promptMsg = msg;
	}
	
	public Map getData(){
		Map dMap = new HashMap();
		dMap.put("promptResult", this.isResult());
		dMap.put("promptMsg", this.getPromptMsg());
		return dMap;
	}
}
