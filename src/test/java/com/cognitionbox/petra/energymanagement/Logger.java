package com.cognitionbox.petra.energymanagement;

import com.cognitionbox.petra.ast.terms.Base;

@Base public class Logger {

    public boolean na(){return true;}
    public void logUsageToReportHigh(){
        if (na()){
            CsvLogger.log(this.getClass().getSimpleName(),"HIGH");
            assert(na());
        }
    }
    public void logUsageToReportLow(){
        if (na()){
            CsvLogger.log(this.getClass().getSimpleName(),"LOW");
            assert(na());
        }
    }
}
