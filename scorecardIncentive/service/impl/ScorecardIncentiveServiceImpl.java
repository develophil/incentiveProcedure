package kr.co.juvis.scorecardIncentive.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.juvis.common.base.service.BaseService;
import kr.co.juvis.common.util.UtilDate;
import kr.co.juvis.formCostSheet.service.FormCostSheetService;
import kr.co.juvis.pubCodeNew.service.PubCodeNewService;
import kr.co.juvis.pubCodeNew.vo.PubCodeNewVO;
import kr.co.juvis.scorecardIncentive.mapper.ScorecardIncentiveMapper;
import kr.co.juvis.scorecardIncentive.service.ScorecardIncentiveService;
import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.IncentiveProcedure;
import kr.co.juvis.scorecardIncentive.util.IncentiveProcedureFactory;
import kr.co.juvis.scorecardIncentive.vo.IncentiveParams;
import kr.co.juvis.scorecardIncentive.vo.ScorecardIncentiveVO;


/**
 * Class Name : ScorecardIncentiveServiceImpl
 * Description : 성과급 계산 정보 비즈니스 서비스 구현 클래스
 * Modification Information
 *
 * @author hkp
 * @since 2017. 01. 24
 * @version 1.0
 *
 */

@Service
public class ScorecardIncentiveServiceImpl extends BaseService implements ScorecardIncentiveService {

	@Autowired
	private ScorecardIncentiveMapper scorecardIncentiveMapper;

	@Autowired
	private FormCostSheetService formCostSheetService;

	@Autowired
	private PubCodeNewService pubService;

	@Autowired
	private IncentiveProcedureFactory incentiveProcedureFactory;

	@Override
	public Map<String,Object> selectScorecardResultSalesR(ScorecardIncentiveVO params) {
	    return scorecardIncentiveMapper.selectScorecardResultSalesR(params);
	}

            // 쥬비스지점표준원가표 가져오기
	    private Map<String,Object> getFormCostSheet(String yymm) {
                String sdate = yymm + "-01";
                String edate = UtilDate.getDateTime(UtilDate.getLastDayOfMonth(UtilDate.getStringToDate(sdate)), "yyyy-MM-dd") ;
                return formCostSheetService.getTotalListNew("", sdate, edate);
	    }

	    // 성과급 대상 데이터 가져오기
	    private List<Map<String,Object>> getIncentiveData(String yymm, String s_seq) {
	        Map<String, String> params = new HashMap<>();
                params.put("scrYm",yymm);
                params.put("scrSeq",s_seq);

                return scorecardIncentiveMapper.selectScorecardResultForIncentive(params);
	    }

	    // 성과급 수령 여부 기준 가져오기
	    private Map<String,Object> getBonusRule() {
	        /*
	         * incentive_rule
	         *    - 쥬비스 전체 손익  : juvis_sales : 6억
	         *    - 지점 손익       : brc_sales   : 2천만원
	         *    - 지점 성과표 점수  : brc_score   : 80점
	         *    - 개인 성과표 점수  : stf_score   : 80점
	         *    - 차트평가 과락 기준 : chart_rate  : 59%
	         */
                 PubCodeNewVO pub = new PubCodeNewVO();
                 pub.setPubGroup("bonus_rule");
                 pub.setUseYn("Y");
                 return pubService.getPubCodeData(pub);
	    }

	    @Override
	    public Map<String,Object> setBonusCalc(String yymm, String s_seq) {

	        Map<String,Object> returnMap = new HashMap<>();

	        /*
	         *  1. 성과급제도
	         *    1) 지점 성과표 점수 80점 이상일 경우부터 지급 대상
	         *    2) 지점 손익 : 2천만원 이상일 경우부터 지급
	         *    3) 개인 성과급 기준 점수 80점 이상인 대상자들만 수령
	         *      : 직급별 1점에 해당하는 성과급 요율에 따라 지급
	         *    4) 미시행 항목이 있을 시 지급 대상자에서 제외 ( 한 항목 0점 )
	         *    5) 필수항목 : 근태(근무시간, 교육시간), 마케팅 실행률 100% (본사 그룹장, 상무, 본부장 제외), 용모(복장, 헤어, 네일 등) 규정 어길 시 성과급 지급 제외
	         *
	         *  2. 성과표 항목
	         *    1) 지점 성과표, 개인 성과표 항목 동일
	         *    2) 기여 손실도 별도로 운영하지 않음
	         */

	        IncentiveParams incentiveParams = new IncentiveParams();
	        incentiveParams.setYymm(yymm);
	        incentiveParams.setSeq(s_seq);

	        /*
	         * 쥬비스지점표준원가표 세팅하기
	         * allS : 전체 손익
	         * S : 매출손익
	         * S001 : 목표매출
	         * S002 : 현매출
	         */
	        incentiveParams.setCostSheet(getFormCostSheet(yymm));

                // 성과급 수령 여부 기준 세팅하기
	        incentiveParams.setBonusRule(getBonusRule());

	        // 지점 직급별 인원 수 세팅하기
	        incentiveParams.setStlCountList(getStlCountList(yymm, s_seq));

                // 성과급 계산을 위한 기초데이터 가져오기.
                List<Map<String,Object>> data =  getIncentiveData(yymm, s_seq);

                IncentiveProcedure iv = null;
                String brcId = null;
                String stfId = null;
                String stlId = null;

                for(Map<String, Object> map : data) {
                    try
                    {
                        stfId = (String)map.get("stfId");
                        stlId = (String)map.get("stlId");
                        brcId = (String)map.get("brcId");
                        iv = incentiveProcedureFactory.getIncentiveProcedure(brcId, stlId);

                        // 지점 데이터
                        if("".equals(stfId) && !"".equals(brcId)) {

                            // 데이터 세팅
                            incentiveParams.setBrcData(map);

                            // 성과급 수령 가능 여부 판단
                            iv.valid(incentiveParams);

                        // 개인 데이터
                        }else {
                            // 데이터 세팅
                            incentiveParams.setStfData(map);

                            // 성과급 수령 가능 여부 판단
                            iv.valid(incentiveParams);

                            // 성과급 계산
                            iv.calculate(incentiveParams);

                            // 성과급 저장
                            iv.confirm(incentiveParams);

                            ScorecardIncentiveVO stfVO = incentiveParams.getIncentiveInsertVO();
                            returnMap.put(stfVO.getStfId(), stfVO.getSccNote());
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } // end for

	        return returnMap;
	    }

    private List<Map<String, Object>> getStlCountList(String yymm,String s_seq)
        {
            Map<String,Object> params = new HashMap<>();
            params.put("yymm", yymm);
            params.put("seq", s_seq);

            List<String> stlList = new ArrayList<>();
            stlList.add("B007");        // 그룹장
            stlList.add("B120");        // 점장
            stlList.add("B151");        // 책임
            params.put("stlList", stlList);

            return scorecardIncentiveMapper.getStlCountList(params);
        }

    /**
	 * 성과급 계산 정보 카운트 조회
	 * @param vo
	 * @return int
	 */
	@Override
    public int getScorecardIncentiveCount(ScorecardIncentiveVO vo) {
		return scorecardIncentiveMapper.selectScorecardIncentiveCount(vo);
	}

	/**
	 * 성과급 계산 정보 상세 조회
	 * @param vo
	 * @return Map<String,Object>
	 */
	@Override
    public Map<String,Object> getScorecardIncentive(ScorecardIncentiveVO vo) {
		return scorecardIncentiveMapper.selectScorecardIncentive(vo);
	}

	/**
	 * 성과급 계산 정보 목록 조회
	 * @param vo
	 * @return List<Map<String,Object>>
	 */
	@Override
    public List<Map<String,Object>> getScorecardIncentiveList(ScorecardIncentiveVO vo) {
		return scorecardIncentiveMapper.selectScorecardIncentiveList(vo);
	}

	/**
	 * 성과급 계산 정보 입력
	 * @param vo
	 * @return int
	 */
	@Override
    public int insertScorecardIncentive(ScorecardIncentiveVO vo) {
		return scorecardIncentiveMapper.insertScorecardIncentive(vo);
	}

	/**
	 * 성과급 계산 정보 수정
	 * @param vo
	 * @return int
	 */
	@Override
    public int updateScorecardIncentive(ScorecardIncentiveVO vo) {
		return scorecardIncentiveMapper.updateScorecardIncentive(vo);
	}

	/**
	 * 성과급 계산 정보 삭제
	 * @param vo
	 * @return int
	 */
	@Override
    public int deleteScorecardIncentive(ScorecardIncentiveVO vo) {
		return scorecardIncentiveMapper.deleteScorecardIncentive(vo);
	}

}