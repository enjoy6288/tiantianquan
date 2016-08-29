package support.base.dao.mapper;


import java.util.List;

import support.base.pojo.po.SweetUser;
import support.base.pojo.vo.SweetCollectVo;
import support.base.pojo.vo.SweetUserVo;

public interface SweetUserMapper {
	int saveUser(SweetUser sweetUser);

	SweetUser queryUser(SweetUserVo vo);

	int saveCollect(List<SweetCollectVo> vos);
	
	int updateUser(SweetUserVo vo);
	
	int delCollect(List<String> ids);
	
}