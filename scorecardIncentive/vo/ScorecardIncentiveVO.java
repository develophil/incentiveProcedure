package kr.co.juvis.scorecardIncentive.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import kr.co.juvis.common.base.vo.BaseVO;


/**
 * Class Name : ScorecardIncentiveVO
 * Description : 성과급 계산 정보 VO 클래스
 * Modification Information
 *
 * @author hkp
 * @since 2017. 01. 24
 * @version 1.0
 *
 */

@Alias("scorecardIncentiveVO")
public class ScorecardIncentiveVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	private Long sccId;
	private String sccYm;
	private String stlType;
	private String brcId;
	private String stlId;
	private String stfId;
	private String sccScore;
	private String sccGradeBefore;
	private String sccGrade;
	private double sccFactor;
	private Long sccIncentive;
	private double sccActRate;
	private Long sccIncentivePost;
	private String sccNote;
	private String sccStfId;
	private Date sccRegDate;
	private Date sccUdtDate;
	private String stfCompany;
	private String seq;

	private int chartRate;         // 차트달성률
        private int appearanceCnt;     // 용모 적발 건수
        private int worktimeCnt;       // 근태 적발 건수
        private int mktPlanCnt;        // 마케팅 실행 목표 건수
        private int mktDoCnt;          // 마케팅 실행 건수

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

    public int getChartRate()
    {
        return chartRate;
    }

    public void setChartRate(int chartRate)
    {
        this.chartRate = chartRate;
    }

    public String getSeq()
    {
        return seq;
    }

    public void setSeq(String seq)
    {
        this.seq = seq;
    }

    /**
	 *  setter
	 * @param sccId as Long
	 */
	public void setSccId(Long sccId) {
		this.sccId = sccId;
	}

	/**
	 *  getter
	 * @return Long
	 */
	public Long getSccId() {
		return this.sccId;
	}

	/**
	 *  setter
	 * @param sccYm as String
	 */
	public void setSccYm(String sccYm) {
		this.sccYm = sccYm;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getSccYm() {
		return this.sccYm;
	}

	/**
	 *  setter
	 * @param stlType as String
	 */
	public void setStlType(String stlType) {
		this.stlType = stlType;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getStlType() {
		return this.stlType;
	}

	/**
	 *  setter
	 * @param brcId as String
	 */
	public void setBrcId(String brcId) {
		this.brcId = brcId;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getBrcId() {
		return this.brcId;
	}

	/**
	 *  setter
	 * @param stlId as String
	 */
	public void setStlId(String stlId) {
		this.stlId = stlId;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getStlId() {
		return this.stlId;
	}

	/**
	 *  setter
	 * @param stfId as String
	 */
	public void setStfId(String stfId) {
		this.stfId = stfId;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getStfId() {
		return this.stfId;
	}

	/**
	 *  setter
	 * @param sccScore as String
	 */
	public void setSccScore(String sccScore) {
		this.sccScore = sccScore;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getSccScore() {
		return this.sccScore;
	}
	/**
	 *  setter
	 * @param sccScore as String
	 */
	public void setScrScore(Double scrScore) {
	    this.sccScore = String.valueOf(scrScore);
	}

	/**
	 *  getter
	 * @return String
	 */
	public Double getScrScore() {
	    return Double.parseDouble(this.sccScore);
	}

	/**
	 *  setter
	 * @param sccGradeBefore as String
	 */
	public void setSccGradeBefore(String sccGradeBefore) {
		this.sccGradeBefore = sccGradeBefore;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getSccGradeBefore() {
		return this.sccGradeBefore;
	}

	/**
	 *  setter
	 * @param sccGrade as String
	 */
	public void setSccGrade(String sccGrade) {
		this.sccGrade = sccGrade;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getSccGrade() {
		return this.sccGrade;
	}

	/**
	 * 평가계수 setter
	 * @param sccFactor as String
	 */
	public void setSccFactor(double sccFactor) {
		this.sccFactor = sccFactor;
	}

	/**
	 * 평가계수 getter
	 * @return double
	 */
	public double getSccFactor() {
		return this.sccFactor;
	}

	/**
	 *  setter
	 * @param sccIncentive as Long
	 */
	public void setSccIncentive(Long sccIncentive) {
		this.sccIncentive = sccIncentive;
	}

	/**
	 *  getter
	 * @return Long
	 */
	public Long getSccIncentive() {
		return this.sccIncentive;
	}

	/**
	 *  setter
	 * @param sccActRate as String
	 */
	public void setSccActRate(double sccActRate) {
		this.sccActRate = sccActRate;
	}

	/**
	 *  getter
	 * @return double
	 */
	public double getSccActRate() {
		return this.sccActRate;
	}

	/**
	 *  setter
	 * @param sccIncentivePost as Long
	 */
	public void setSccIncentivePost(Long sccIncentivePost) {
		this.sccIncentivePost = sccIncentivePost;
	}

	/**
	 *  getter
	 * @return Long
	 */
	public Long getSccIncentivePost() {
		return this.sccIncentivePost;
	}

	/**
	 *  setter
	 * @param sccNote as String
	 */
	public void setSccNote(String sccNote) {
		this.sccNote = sccNote;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getSccNote() {
		return this.sccNote;
	}

	/**
	 *  setter
	 * @param sccStfId as String
	 */
	public void setSccStfId(String sccStfId) {
		this.sccStfId = sccStfId;
	}

	/**
	 *  getter
	 * @return String
	 */
	public String getSccStfId() {
		return this.sccStfId;
	}

	/**
	 *  setter
	 * @param sccRegDate as Date
	 */
	public void setSccRegDate(Date sccRegDate) {
		this.sccRegDate = sccRegDate;
	}

	/**
	 *  getter
	 * @return Date
	 */
	public Date getSccRegDate() {
		return this.sccRegDate;
	}

	/**
	 *  setter
	 * @param sccUdtDate as Date
	 */
	public void setSccUdtDate(Date sccUdtDate) {
		this.sccUdtDate = sccUdtDate;
	}

	/**
	 *  getter
	 * @return Date
	 */
	public Date getSccUdtDate() {
		return this.sccUdtDate;
	}

	/**
	 * 법인 setter
	 * @param stfCompany as String
	 */
	public void setStfCompany(String stfCompany) {
		this.stfCompany = stfCompany;
	}

	/**
	 * 법인 getter
	 * @return String
	 */
	public String getStfCompany() {
		return this.stfCompany;
	}




}