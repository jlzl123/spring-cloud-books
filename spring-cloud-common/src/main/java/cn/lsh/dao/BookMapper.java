package cn.lsh.dao;

import cn.lsh.entity.Book;
import cn.lsh.entity.BookCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    long countByExample(BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    int deleteByExample(BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    int deleteByPrimaryKey(Integer bookId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    int insert(Book record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    int insertSelective(Book record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    List<Book> selectByExample(BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    Book selectByPrimaryKey(Integer bookId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    int updateByExampleSelective(@Param("record") Book record, @Param("example") BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    int updateByExample(@Param("record") Book record, @Param("example") BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    int updateByPrimaryKeySelective(Book record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbg.generated Tue Aug 08 15:24:09 CST 2017
     */
    int updateByPrimaryKey(Book record);
}