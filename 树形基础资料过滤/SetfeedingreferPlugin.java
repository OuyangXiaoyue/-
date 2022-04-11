package wens.breeding.standardizedwork.plugin.seting;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.form.field.BasedataEdit;
import kd.bos.form.field.events.BeforeF7SelectEvent;
import kd.bos.form.field.events.BeforeF7SelectListener;
import kd.bos.list.ListShowParameter;

import java.util.EventObject;

/**
 * @author OuyangXiaoyue
 * @Description 饲养参考标准设置
 * @site: wens.breeding.standardizedwork.plugin.seting.SetfeedingreferPlugin
 **/
public class SetfeedingreferPlugin extends AbstractBillPlugIn implements BeforeF7SelectListener {

    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        BasedataEdit variety = this.getView().getControl("wens_variety");    // 品种
        variety.addBeforeF7SelectListener(this);
    }

    @Override
    public void beforeF7Select(BeforeF7SelectEvent e) {
        ListShowParameter param = (ListShowParameter) e.getFormShowParameter();
        switch (e.getProperty().getName()) {
            case "wens_variety":
                param.addCustPlugin("wens.breeding.standardizedwork.share.MaterialLeftTreeFilterPlugin");
                break;
            default:
                break;
        }
    }
}
