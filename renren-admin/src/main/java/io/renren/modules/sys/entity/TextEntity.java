package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("tb_text")
public class TextEntity {
    private String name;
    private String Text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
