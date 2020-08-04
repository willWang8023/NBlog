package top.naccl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.naccl.entity.Blog;

import java.util.List;

/**
 * @Description: 博客文章持久层接口
 * @Author: Naccl
 * @Date: 2020-07-26
 */
@Mapper
@Repository
public interface BlogMapper {
	List<Blog> getListByTitleOrCategoryId(String title, Integer CategoryId);

	List<Blog> getIdAndTitleList();

	int deleteBlogById(Long id);

	int deleteBlogTagByBlogId(Long blogId);

	int saveBlog(Blog blog);

	int saveBlogTag(Long blogId, Long tagId);

	int updateBlogRecommendById(Long blogId, Boolean recommend);

	int updateBlogPublishedById(Long blogId, Boolean published);

	Blog getBlogById(Long id);

	int updateBlog(Blog blog);

	int countBlogByCategoryId(Long categoryId);

	int countBlogByTagId(Long tagId);
}