package kr.co.juvis.scorecardIncentive.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.juvis.scorecardIncentive.vo.ScorecardIncentiveVO;


/**
 * Class Name : ScorecardIncentiveMapper
 * Description : 성과급 계산 정보 Mapper 클래스
 * Modification Information
 *
 * @author hkp
 * @since 2017. 01. 24
 * @version 1.0
 *
 */

@Repository
public interface ScorecardIncentiveMapper {

	/**
	 * 성과급 계산 정보 카운트 조회
	 * @param vo
	 * @return Integer
	 */
	public Integer selectScorecardIncentiveCount(ScorecardIncentiveVO vo);

	/**
	 * 성과급 계산 정보 조회
	 * @param vo
	 * @return Map<String,Object>
	 */
	public Map<String,Object> selectScorecardIncentive(ScorecardIncentiveVO vo);

	/**
	 * 성과급 계산 정보 목록 조회
	 * @param vo
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> selectScorecardIncentiveList(ScorecardIncentiveVO vo);

	/**
	 * 성과급 계산 정보 등록
	 * @param vo
	 * @return int
	 */
	public int insertScorecardIncentive(ScorecardIncentiveVO vo);
	public int insertOrUpdateScorecardIncentive(ScorecardIncentiveVO vo);

	/**
	 * 성과급 계산 정보 수정
	 * @param vo
	 * @return int
	 */
	public int updateScorecardIncentive(ScorecardIncentiveVO vo);

	/**
	 * 성과급 계산 정보 삭제
	 * @param vo
	 * @return int
	 */
	public int deleteScorecardIncentive(ScorecardIncentiveVO vo);


        /* 성과급 계산 관련 */
        /**
         * 저장된 성과급 기준 데이터 가져오기
         * @param param
         * @return
         */
        public List<Map<String, Object>> selectScorecardResultForIncentive(Map<String, String> param);
        /**
         * 저장된 성과급 기준 지점 데이터 가져오기
         * @param param
         * @return
         */
        public List<Map<String, Object>> selectScorecardResultForBrcIncentive(Map<String, String> param);

        /**
         * 성과급 계산용 지점 매출 데이터
         * @param params
         * @return
         */
        public Map<String, Object> selectScorecardResultSalesR(ScorecardIncentiveVO params);


        /**
         * 미시행 항목 리스트 가져오기
         * @param incentiveParams
         * @return
         */
        public List<Map<String, Object>> getDoNotScorecardItemList(ScorecardIncentiveVO incentiveParams);

        /**
         * * 지점별 존재하는 직급 인원수 목록 가져오기.
         * @param params
         * @return
         */
        public List<Map<String, Object>> getStlCountList(Map<String,Object> params);

}