package kr.co.juvis.scorecardIncentive.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jxl.format.Colour;
import kr.co.juvis.common.base.web.BaseController;
import kr.co.juvis.common.util.JuvisConst.ExcelConst;
import kr.co.juvis.common.util.UtilDate;
import kr.co.juvis.formCostSheet.service.FormCostSheetService;
import kr.co.juvis.scorecardIncentive.mapper.ScorecardIncentiveMapper;
import kr.co.juvis.scorecardIncentive.service.ScorecardIncentiveService;
import kr.co.juvis.scorecardIncentive.vo.ScorecardIncentiveVO;


/**
 * Class Name : ScorecardIncentiveController
 * Description : 성과급 계산 정보 컨트롤러 클래스
 * Modification Information
 *
 * @author hkp
 * @since 2017. 01. 24
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/scorecardIncentive")
public class ScorecardIncentiveController extends BaseController {

	@Autowired
	private ScorecardIncentiveService scorecardIncentiveService;

	@Autowired
	private ScorecardIncentiveMapper scorecardIncentiveMapper;

	@Autowired
	private FormCostSheetService formCostSheetService;


	/**
	 * 성과급 계산 정보 목록 조회
	 * @param vo
	 * @return
	 */
	@RequestMapping("/test")
	public @ResponseBody  Map<String,Object> test(ScorecardIncentiveVO vo) {
		Map<String,Object> map = new HashMap<String,Object>();

		String yymm = "2016-06";
		String seq = "4";
		if(vo!=null) {
		    if(vo.getSccYm()!=null) yymm = vo.getSccYm();
		    if(vo.getSeq()!=null) seq = vo.getSeq();
		}
		map = scorecardIncentiveService.setBonusCalc(yymm, seq);

		return map;
	}

	@RequestMapping("/testCost")
	public @ResponseBody  Map<String,Object> testCost(ScorecardIncentiveVO vo) {

	    String yymm = "2016-06";

	    if(vo!=null) {
	        if(vo.getSccYm()!=null) yymm = vo.getSccYm();
	    }

            String sdate = yymm + "-01";
            String edate = UtilDate.getDateTime(UtilDate.getLastDayOfMonth(UtilDate.getStringToDate(sdate)), "yyyy-MM-dd") ;

            return formCostSheetService.getTotalListNew("", sdate, edate);
	}
	@RequestMapping("/test/db")
	public @ResponseBody  List<Map<String,Object>> testdb(ScorecardIncentiveVO vo) {

	    String yymm = "2016-06";
            String seq = "4";
            if(vo!=null) {
                if(vo.getSccYm()!=null) yymm = vo.getSccYm();
                if(vo.getSeq()!=null) seq = vo.getSeq();
            }

            Map<String, String> params = new HashMap<>();
            params.put("scrYm",yymm);
            params.put("scrSeq",seq);

            // scorecardResult total 결과 가져오기.
            return scorecardIncentiveMapper.selectScorecardResultForBrcIncentive(params);
	}

	/**
	 * 성과급 계산 정보 목록 조회
	 * @param vo
	 * @return
	 */
	@RequestMapping("/scorecardIncentiveList")
	public @ResponseBody  Map<String,Object> list(@RequestBody ScorecardIncentiveVO vo) {
	    Map<String,Object> map = new HashMap<String,Object>();

	    int totalCount = scorecardIncentiveService.getScorecardIncentiveCount(vo);
	    vo.setTotalCount(totalCount);
	    map.put("totalCount", totalCount);
	    map.put("list", scorecardIncentiveService.getScorecardIncentiveList(vo));

	    return map;
	}

	/**
	 * 성과급 계산 정보 상세 조회
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping("/scorecardIncentiveView")
	public @ResponseBody  Map<String,Object> view(@RequestBody ScorecardIncentiveVO vo) {
		return scorecardIncentiveService.getScorecardIncentive(vo);
	}

	/**
	 * 성과급 계산 정보 입력 처리
	 * @param vo
	 * @return Integer 1이상이면 성공
	 */
	@RequestMapping("/scorecardIncentiveInsert")
	public @ResponseBody Integer insert(@RequestBody ScorecardIncentiveVO vo) {
		return scorecardIncentiveService.insertScorecardIncentive(vo);
	}

	/**
	 * 성과급 계산 정보 수정 처리
	 * @param vo
	 * @return Integer 1이상이면 성공
	 */
	@RequestMapping("/scorecardIncentiveUpdate")
	public @ResponseBody Integer update(@RequestBody ScorecardIncentiveVO vo) {
		return scorecardIncentiveService.updateScorecardIncentive(vo);
	}

	/**
	 * 성과급 계산 정보 삭제
	 * @param vo
	 * @return Integer 1이상이면 성공
	 */
	@RequestMapping("/scorecardIncentiveDelete")
	public @ResponseBody Integer delete(@RequestBody ScorecardIncentiveVO vo) {
		return scorecardIncentiveService.deleteScorecardIncentive(vo);
	}

	/**
	 * 성과급 계산 정보 Excel Download
	 * @param vo
	 * @param model
	 * @return String
	 */
	@RequestMapping("/scorecardIncentiveDownExcel")
	public String downExcel(@ModelAttribute("vo") ScorecardIncentiveVO vo, ModelMap model) {

		int totalRecordCount = scorecardIncentiveService.getScorecardIncentiveCount(vo);

		vo.setFirstIndex(0);
		vo.setLastIndex(totalRecordCount);

		List<Map<String,Object>> list = scorecardIncentiveService.getScorecardIncentiveList(vo);


		// 엑셀데이터를 해쉬맵 형태로 정의합니다.
		Map<String, Object> excelData = new HashMap<String, Object>();

		// 엑셀파일명을 포팅 포팅될때는 파일이름 뒤에 날짜가 자동으로 붙는다 저장시 수정해 주면 됨. - 파��명은 타이틀명도 겸함.
		excelData.put(ExcelConst.ExcelFileName, "샘플목록");

		// 셀의 가로크기를 정해줍니다.(int) - 문자수기준
		excelData.put(ExcelConst.ExcelCellWidth, 30);

		// 컬럼헤더의 색상을 정해줍니다. jxl.format.Colour
		excelData.put(ExcelConst.ExcelCellColor, Colour.LIGHT_ORANGE);

		// 엑셀의 컬럼 헤드를 정의해 줍니다. 정의되는 VO 의 프로퍼티명(필드명,멤버변수명)을 Key로 반드시 지정해 주셔야 합니다.
		// 또한, LikedHashMap<String,String> 으로 정의해 주셔야 합니다.
		LinkedHashMap<String, String> columnHeader = new LinkedHashMap<String, String>();
		columnHeader.put("id", "일련번호");
		columnHeader.put("title", "제목");
		columnHeader.put("content", "내용");
		columnHeader.put("writer", "작성자");
		columnHeader.put("visit", "조회수");
		columnHeader.put("creId", "작성자아이디");
		columnHeader.put("creDt", "작성일");
		columnHeader.put("updId", "수��자아이디");
		columnHeader.put("updDt", "수정일");

		// 컬럼 헤드를 엑셀데이터에 담습니다.
		excelData.put(ExcelConst.ColumnNameList, columnHeader);
		// 실제 셀들의 데이터 값을 엑셀데이터에 담습니다.
		excelData.put(ExcelConst.CellDataList, list);

		model.addAttribute(ExcelConst.ExcelData, excelData);

		// 엑셀 다운로드를 실행 합니다. ExcelConst.ExcelView 리턴은 고정입니다.
		return ExcelConst.ExcelView;
	}

}