package model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * mongo对象
 * Created by xueyunlong on 17-4-19.
 */
public class MongoModel implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    private String primaryKey;

    private int type;

    private Date createTime;
}
