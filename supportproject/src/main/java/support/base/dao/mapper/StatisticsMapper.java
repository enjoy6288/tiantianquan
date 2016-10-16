package support.base.dao.mapper;

import java.util.List;

import support.base.pojo.po.SweetStatistics;
import support.base.pojo.vo.SweetStatisticsVo;

public interface StatisticsMapper {
	List<SweetStatistics> queryStatistics(SweetStatisticsVo vo);
	void saveStatistics(SweetStatistics statistics);
}