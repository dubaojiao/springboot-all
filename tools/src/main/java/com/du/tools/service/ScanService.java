package com.du.tools.service;

import com.du.tools.domain.ScanData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScanService {
    private List<ScanData> scanDataList;

    public List<ScanData> getScanDataList() {
        System.out.println("getScanDataList");
        scanDataList = new ArrayList<>();
        for(int x=0;x<20;x++){
            ScanData scanData = new ScanData();
            scanData.setId(x+1);
            scanData.setContent("sshsauhdhsasdjsadhjkasldhaj速度就卡死的啥借口打折的接口");
            scanData.setTime("2018-02-03 23:10:20");
            scanData.setType(1);
            scanData.setTypeName("字符串");
            scanDataList.add(scanData);
        }
        return scanDataList;
    }
}
