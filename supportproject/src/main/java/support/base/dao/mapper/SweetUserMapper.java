package support.base.dao.mapper;


import support.base.pojo.po.SweetCollect;
import support.base.pojo.po.SweetUser;
import support.base.pojo.vo.SweetUserVo;

public interface SweetUserMapper {
	int saveUser(SweetUser sweetUser);

	SweetUser queryUser(SweetUserVo vo);

	int saveCollect(SweetCollect sweetCollect);
	
	int updateUser(SweetUserVo vo);

	
}