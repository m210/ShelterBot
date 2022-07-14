package sky.pro.shelterbot.handler;

import java.util.HashMap;
import java.util.Map;

import sky.pro.shelterbot.message.report.ReportMessage;
import sky.pro.shelterbot.model.ReportStage;
import sky.pro.shelterbot.response.ResponseMessage;
import sky.pro.shelterbot.service.ReportService;

public class ReportHandler {
	
	private Map<ReportStage, ResponseMessage> map = new HashMap<>();
	private ReportStage reportStage = ReportStage.COMPLETE;
	
	public void init(ReportService reportService) {
		map.put(ReportStage.PHOTO, ResponseMessage.PHOTO_REPORTMESSAGE);
		map.put(ReportStage.RATION, ResponseMessage.RATION_REPORTMESSAGE);
		map.put(ReportStage.HEALTH, ResponseMessage.HEALTH_REPORTMESSAGE);
		map.put(ReportStage.BEHAVIOR, ResponseMessage.BEHAVIOR_REPORTMESSAGE);
		map.put(ReportStage.COMPLETE, ResponseMessage.COMPLETE_REPORTMESSAGE);
		
		for(ResponseMessage message : map.values()) {
			((ReportMessage) message.getMessage()).setService(reportService);
		}
	}
	
	public boolean requireReport() {
		return reportStage != ReportStage.COMPLETE;
	}
	
	public void startReport() {
		reportStage = ReportStage.PHOTO;
	}
	
	public void cancelReport() {
		reportStage = ReportStage.COMPLETE;
	}
	
	public boolean processReport(UserMessage userMessage) {
		if(userMessage.getMessage() != null && userMessage.getMessage().equals("Отмена")) {
			cancelReport();
            return false;
        }
		
		ReportMessage message = (ReportMessage) map.get(reportStage).getMessage();
	    if(!message.processReport(userMessage)) {
	         message.send(userMessage.getUserId());
	    } else {
	        reportStage = ReportStage.values()[reportStage.ordinal() + 1]; // next state
	        map.get(reportStage).send(userMessage.getUserId());
	    }
	    
	    return true;
	}
}
