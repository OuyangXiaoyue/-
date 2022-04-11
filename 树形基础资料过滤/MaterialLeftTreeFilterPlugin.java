package wens.breeding.standardizedwork.share;

import kd.bos.entity.tree.TreeNode;
import kd.bos.form.control.events.RefreshNodeEvent;
import kd.bos.form.events.SetFilterEvent;
import kd.bos.list.plugin.AbstractTreeListPlugin;
import kd.bos.mvc.list.TreeListModel;
import kd.bos.orm.query.QFilter;

import java.util.List;

/**
 *@ClassName：MaterialLeftTreeFilterPlugin
 *@Description:左树过滤-只显示饲养品种数据
 *@site:wens.breeding.standardizedwork.share.MaterialLeftTreeFilterPlugin
 **/
public class MaterialLeftTreeFilterPlugin extends AbstractTreeListPlugin {
    /**
    *@Author: OuyangXiaoyue
    *@phone:  13265986134
    *@Date:   2021/6/29 9:52
    *@Method: refreshNode
    *@Param:  * @param null
    *@Description:左树过滤-只显示饲养品种数据
    **/
    @Override
    public void refreshNode(RefreshNodeEvent e) {
        if("8609760E-EF83-4775-A9FF-CCDEC7C0B689".equals(e.getNodeId())) {
            TreeListModel treeModel = (TreeListModel) this.getTreeListView().getTreeModel();
            List<QFilter> filters = treeModel.getTreeFilter();
                filters.add(new QFilter("number", QFilter.like, "07%"));
                e.setChildNodes(null);
        }else {
            TreeNode treeNode = getTreeModel().getRoot().getTreeNode((String) e.getNodeId(), 10);
            e.setChildNodes(treeNode.getChildren());
        }
    }
    @Override
    public void setFilter(SetFilterEvent e) {
       e.getQFilters().add(new QFilter("number", QFilter.like, "07%"));
    }
}
