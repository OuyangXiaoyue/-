package gzgg.em.cost.plugin.apply;

import java.util.List;
import kd.bos.form.events.SetFilterEvent;
import kd.bos.list.IListView;
import kd.bos.list.events.BeforeShowBillFormEvent;
import kd.bos.list.plugin.AbstractListPlugin;
import kd.bos.orm.query.QFilter;
/**
 * 
 * 作者： OuyangXiaoyue
 * 日期：2021-02-05
 * 描述：费用申请单列表插件
 *
 */
public class DailyapplyBillList extends AbstractListPlugin{

	/**
	 * 
	 * 描述：将单据类型传入子页面
	 * @author OuyangXiaoyue
	 * @date：2021年2月25日 上午10:05:45 
	 * @phone:13265986134
	 */
	@Override
	public void beforeShowBill(BeforeShowBillFormEvent e) {
		String billtype = this.getView().getFormShowParameter().getCustomParam("billType");// 获取菜单参数
		e.getParameter().setCustomParam("billtype",billtype);	// 将参数传到子页面
		super.beforeShowBill(e);
	}

	/**
	 * 
	 * 描述：根据参数过滤数据
	 * @author OuyangXiaoyue
	 * @date：2021年2月25日 上午10:07:59 
	 * @phone:13265986134
	 */
	@Override
	public void setFilter(SetFilterEvent e) {
		super.setFilter(e);
		List<QFilter> qfilterList =e.getQFilters();
		IListView listview = (IListView)this.getView();
		String logo = listview.getBillFormId();	// 获取列表绑定的单据
		if(logo != null && "er_dailyapplybill".equals(logo)) {
			qfilterList.add(new QFilter("gz_billtypefield.number", QFilter.equals, "er_dailyapplybill_BT"));
		}else if(logo != null && "gz_no_dailyapplybill".equals(logo)) {
			qfilterList.add(new QFilter("gz_billtypefield.number", QFilter.equals, "er_dailyapplybill_BT2"));
		}
	}
}
