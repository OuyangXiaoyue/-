package wens.breeding.standardizedwork.share;

import kd.bos.context.RequestContext;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.entity.plugin.AbstractOperationServicePlugIn;
import kd.bos.entity.plugin.PreparePropertysEventArgs;
import kd.bos.entity.plugin.args.BeginOperationTransactionArgs;

import java.util.Date;

/**
 *@ClassName：aduitOp
 *@Description:监听审核人信息
 *@site:wens.breeding.standardizedwork.share.aduitOp
 */
public class aduitOp extends AbstractOperationServicePlugIn {

   /**
   *@Author: OuyangXiaoyue
   *@phone:  13265986134
   *@Date:   2021/6/16 15:28
   *@Method: onPreparePropertys
   *@Param:  * @param null
   *@Description:监听审核人和审核时间
   **/
    @Override
    public void onPreparePropertys(PreparePropertysEventArgs e) {
        super.onPreparePropertys(e);
        e.getFieldKeys().add("wens_audituser");
        e.getFieldKeys().add("wens_auditdate");
    }

    @Override
    public void beginOperationTransaction(BeginOperationTransactionArgs e) {
        super.beginOperationTransaction(e);
        if ("audit".equals(e.getOperationKey())) {
            DynamicObject[] dataEntities = e.getDataEntities();
            this.getImageNo(dataEntities);
        }
    }
    /**
    *@Author: OuyangXiaoyue
    *@phone:  13265986134
    *@Date:   2021/6/16 15:29
    *@Method: getImageNo
    *@Param:  * @param null
    *@Description:审核时添加审核人信息
    **/
    private boolean getImageNo(DynamicObject[] dataEntities) {
        boolean flag = false;
        DynamicObject[] arg2 = dataEntities;
        int arg3 = dataEntities.length;
        for (int arg4 = 0; arg4 < arg3; ++arg4) {
            DynamicObject elem = arg2[arg4];
            elem.getDynamicObjectType().getProperties();
            String userId = RequestContext.get().getUserId();
            elem.set("wens_audituser", userId);
            elem.set("wens_auditdate", new Date());
        }
        return flag;
    }
}
