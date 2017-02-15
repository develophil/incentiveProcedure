package kr.co.juvis.scorecardIncentive.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.IncentiveProcedure;
import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl.IncentiveProcedureForB01;
import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl.IncentiveProcedureForB02;
import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl.IncentiveProcedureForBranch;
import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl.IncentiveProcedureForC01;
import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl.IncentiveProcedureForC02;
import kr.co.juvis.staffLevel.vo.StaffLevel;

@Component
public class IncentiveProcedureFactory
{
    @Autowired  private IncentiveProcedureForBranch incentiveProcedureForBranch;
    @Autowired  private IncentiveProcedureForC01 incentiveProcedureForC01;
    @Autowired  private IncentiveProcedureForC02 incentiveProcedureForC02;
    @Autowired  private IncentiveProcedureForB01 incentiveProcedureForB01;
    @Autowired  private IncentiveProcedureForB02 incentiveProcedureForB02;
    @Autowired  private StaffLevel staffLevel;

    public IncentiveProcedure getIncentiveProcedure(String brcId, String stlId) {
        if(stlId==null || "".equals(stlId)) {
            // 지점
            return incentiveProcedureForBranch;
        }else{
          // 직원
//        String stlGroupId = "group1";
            String stlGroupName = "";
            stlGroupName = staffLevel.getGroupName("incentive", stlId);

            if("center".equals(brcId)) {
                if("C01".equals(stlGroupName)) {
                    return incentiveProcedureForC01;
                }else{
                    return incentiveProcedureForC02;
                }
            }else if("food".equals(brcId)) {
                return incentiveProcedureForB02;
            }else if("osamsung".equals(brcId)) {
                return incentiveProcedureForB02;
            }else {
                if("B01".equals(stlGroupName)) {
                    return incentiveProcedureForB01;
                } else {
                    return incentiveProcedureForB02;
                }
            }
        }
    }

}
