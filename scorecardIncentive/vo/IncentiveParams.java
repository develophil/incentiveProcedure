package kr.co.juvis.scorecardIncentive.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.juvis.auth.util.AuthHelper;
import kr.co.juvis.common.util.UtilString;
import kr.co.juvis.util.MathUtil;

public class IncentiveParams
{

    // 공통 데이터
    private String yymm;
    private String seq;
    private Map<String,Object> costSheet;        // 쥬비스 표준원가표
    private Map<String,Object> bonusRule;        // 성과급 계산 기준
    private List<Map<String,Object>> stlCountList;        // 지점별 해당 직급 인원 수

    // 지점 데이터
    private boolean brcValid;
    private String brcId;
    private double brcScore;
    private List<String> brcMemoList;

    // 개인 데이터
    private boolean stfValid;
    private String stlType;
    private String stlId;
    private String stfId;
    private double stfScore;
    private String sccGrade;
    private String stfCompany;
    private int chartRate;         // 차트달성률
    private int appearanceCnt;     // 용모 적발 건수
    private int worktimeCnt;       // 근태 적발 건수
    private int mktPlanCnt;        // 마케팅 실행 목표 건수
    private int mktDoCnt;          // 마케팅 실행 건수
    private List<String> stfMemoList;

    // 최종 데이터
    private String totalMemo;
    private int actRate;                // 성과급 적용 요율
    private double finalScore;
    private double sccFactor;
    private long sccIncentive;

    public boolean isStfValid()
    {
        return stfValid;
    }
    public void setStfValid(boolean stfValid)
    {
        this.stfValid = stfValid;
    }
    public boolean isTotalValid()
    {
        return brcValid && stfValid;
    }
    public String getSccNote()
    {
        String note = "";
        if(brcMemoList!=null) note += (UtilString.join(", ",brcMemoList) +", ");
        if(stfMemoList!=null) note += UtilString.join(", ", stfMemoList);
        return note;
    }
    public String getTotalMemo()
    {
        return totalMemo;
    }
    public void setTotalMemo(String totalMemo)
    {
        this.totalMemo = totalMemo;
    }
    public boolean isBrcValid()
    {
        return brcValid;
    }
    public void setBrcValid(boolean brcValid)
    {
        this.brcValid = brcValid;
    }
    public List<String> getBrcMemoList()
    {
        return brcMemoList;
    }
    public void setBrcMemoList(List<String> brcMemoList)
    {
        if(this.brcMemoList==null) this.brcMemoList = new ArrayList<>();
        this.brcMemoList = brcMemoList;
    }
    public void addBrcMemo(String memo)
    {
        if(this.brcMemoList==null) this.brcMemoList = new ArrayList<>();
        this.brcMemoList.add(memo);
    }
    public double getFinalScore()
    {
        return finalScore;
    }
    public void setFinalScore(double finalScore)
    {
        this.finalScore = finalScore;
    }
    public String getBrcId()
    {
        return brcId;
    }
    public void setBrcId(String brcId)
    {
        this.brcId = brcId;
    }
    public double getBrcScore()
    {
        return brcScore;
    }
    public void setBrcScore(double brcScore)
    {
        this.brcScore = brcScore;
    }
    public String getYymm()
    {
        return yymm;
    }
    public void setYymm(String yymm)
    {
        this.yymm = yymm;
    }
    public String getSeq()
    {
        return seq;
    }
    public void setSeq(String seq)
    {
        this.seq = seq;
    }
    public String getStlType()
    {
        return stlType;
    }
    public void setStlType(String stlType)
    {
        this.stlType = stlType;
    }
    public String getStlId()
    {
        return stlId;
    }
    public void setStlId(String stlId)
    {
        this.stlId = stlId;
        if(stlId!=null)
            this.stlType = stlId.substring(0,1);
    }
    public String getStfId()
    {
        return stfId;
    }
    public void setStfId(String stfId)
    {
        this.stfId = stfId;
    }
    public double getStfScore()
    {
        return stfScore;
    }
    public void setStfScore(double stfScore)
    {
        this.stfScore = stfScore;
    }
    public String getSccGrade()
    {
        return sccGrade;
    }
    public void setSccGrade(String sccGrade)
    {
        this.sccGrade = sccGrade;
    }
    public double getSccFactor()
    {
        return sccFactor;
    }
    public void setSccFactor(double sccFactor)
    {
        this.sccFactor = sccFactor;
    }
    public long getSccIncentive()
    {
        return sccIncentive;
    }
    public void setSccIncentive(long sccIncentive)
    {
        this.sccIncentive = sccIncentive;
    }
    public String getStfCompany()
    {
        return stfCompany;
    }
    public void setStfCompany(String stfCompany)
    {
        this.stfCompany = stfCompany;
    }
    public int getChartRate()
    {
        return chartRate;
    }
    public void setChartRate(int chartRate)
    {
        this.chartRate = chartRate;
    }
    public int getAppearanceCnt()
    {
        return appearanceCnt;
    }
    public void setAppearanceCnt(int appearanceCnt)
    {
        this.appearanceCnt = appearanceCnt;
    }
    public int getWorktimeCnt()
    {
        return worktimeCnt;
    }
    public void setWorktimeCnt(int worktimeCnt)
    {
        this.worktimeCnt = worktimeCnt;
    }
    public int getMktPlanCnt()
    {
        return mktPlanCnt;
    }
    public void setMktPlanCnt(int mktPlanCnt)
    {
        this.mktPlanCnt = mktPlanCnt;
    }
    public int getMktDoCnt()
    {
        return mktDoCnt;
    }
    public void setMktDoCnt(int mktDoCnt)
    {
        this.mktDoCnt = mktDoCnt;
    }
    public List<Map<String, Object>> getStlCountList()
    {
        return stlCountList;
    }
    public void setStlCountList(List<Map<String, Object>> stlCountList)
    {
        this.stlCountList = stlCountList;
    }
    public int getActRate()
    {
        return actRate;
    }
    public void setActRate(int actRate)
    {
        this.actRate = actRate;
    }
    public List<String> getStfMemoList()
    {
        return stfMemoList;
    }
    public void setStfMemoList(List<String> memoList)
    {
        if(this.stfMemoList==null) this.stfMemoList = new ArrayList<>();
        this.stfMemoList = memoList;
    }
    public void addStfMemo(String memo)
    {
        if(stfMemoList==null) stfMemoList = new ArrayList<>();
        this.stfMemoList.add(memo);
    }
    public Map<String, Object> getCostSheet()
    {
        return costSheet;
    }
    public void setCostSheet(Map<String, Object> costSheet)
    {
        this.costSheet = costSheet;
    }
    public Map<String, Object> getBonusRule()
    {
        return bonusRule;
    }
    public void setBonusRule(Map<String, Object> bonusRule)
    {
        this.bonusRule = bonusRule;
    }

    public ScorecardIncentiveVO getIncentiveInsertVO() {

        ScorecardIncentiveVO stfParam = new ScorecardIncentiveVO();
        stfParam.setSeq(seq);
        stfParam.setSccYm(yymm);
        stfParam.setStfCompany(stfCompany);
        stfParam.setBrcId(brcId);
        stfParam.setStlId(stlId);
        stfParam.setStlType(stlType);
        stfParam.setStfId(stfId);
        stfParam.setSccScore(String.valueOf(getFinalScore()));
        stfParam.setSccGradeBefore(sccGrade);
        stfParam.setSccGrade(sccGrade);
        stfParam.setChartRate(chartRate);
        stfParam.setAppearanceCnt(appearanceCnt);
        stfParam.setWorktimeCnt(worktimeCnt);
        stfParam.setMktPlanCnt(mktPlanCnt);
        stfParam.setMktDoCnt(mktDoCnt);
        stfParam.setSccActRate(actRate);
        stfParam.setSccNote(getSccNote());
        stfParam.setSccFactor(sccFactor);
        stfParam.setSccIncentive(sccIncentive);
        stfParam.setSccIncentivePost(sccIncentive);
        stfParam.setSccStfId(AuthHelper.getStaffId());

        return stfParam;
    }

    public void setStfData(Map<String, Object> map)
    {
        setSccIncentive(0);
        setFinalScore(0);
        setActRate(0);
        setSccFactor(0);
        setStfValid(true);
        setStlId((String)map.get("stlId"));
        setBrcId((String)map.get("brcId"));
        setStfId((String)map.get("stfId"));
        setStfScore(MathUtil.getRoundDouble((double)((float)map.get("scrScore")), 2));
        setSccGrade((String)map.get("scrGrade"));
        setStfCompany((String)map.get("stfCompany"));
        setChartRate(MathUtil.getNumber(map.get("chartRate")));              // 차트달성률
        setAppearanceCnt(MathUtil.getNumber(map.get("appearanceCnt")));      // 용모 적발 건수
        setWorktimeCnt(MathUtil.getNumber(map.get("worktimeCnt")));          // 근태 적발 건수
        setMktPlanCnt(MathUtil.getNumber(map.get("mktPlanCnt")));            // 마케팅 실행 목표 건수
        setMktDoCnt(MathUtil.getNumber(map.get("mktDoCnt")));                // 마케팅 실행 건수
        setStfMemoList(null);
    }



    public void setBrcData(Map<String, Object> map)
    {
        setStfId("");
        setBrcMemoList(null);
        setBrcValid(true);
        setBrcScore(MathUtil.getRoundDouble((double)((float)map.get("scrScore")), 2));
        setBrcId((String)map.get("brcId"));
    }
}
