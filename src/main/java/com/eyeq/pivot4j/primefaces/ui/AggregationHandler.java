package com.eyeq.pivot4j.primefaces.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.olap4j.Axis;
import org.primefaces.model.DualListModel;

import com.eyeq.pivot4j.ui.aggregator.AggregatorPosition;

@ManagedBean(name = "aggregationHandler")
@RequestScoped
public class AggregationHandler {

	@ManagedProperty(value = "#{pivotGridHandler.renderer}")
	private PrimeFacesPivotRenderer renderer;

	private DualListModel<SelectItem> columnAggregators;

	private DualListModel<SelectItem> columnHierarchyAggregators;

	private DualListModel<SelectItem> columnMemberAggregators;

	private DualListModel<SelectItem> rowAggregators;

	private DualListModel<SelectItem> rowHierarchyAggregators;

	private DualListModel<SelectItem> rowMemberAggregators;

	private ResourceBundle bundle;

	@PostConstruct
	protected void initialize() {
		FacesContext context = FacesContext.getCurrentInstance();

		this.bundle = context.getApplication()
				.getResourceBundle(context, "msg");

		this.columnAggregators = createSelectionModel(Axis.COLUMNS,
				AggregatorPosition.Grand);
		this.columnHierarchyAggregators = createSelectionModel(Axis.COLUMNS,
				AggregatorPosition.Hierarchy);
		this.columnMemberAggregators = createSelectionModel(Axis.COLUMNS,
				AggregatorPosition.Member);

		this.rowAggregators = createSelectionModel(Axis.ROWS,
				AggregatorPosition.Grand);
		this.rowHierarchyAggregators = createSelectionModel(Axis.ROWS,
				AggregatorPosition.Hierarchy);
		this.rowMemberAggregators = createSelectionModel(Axis.ROWS,
				AggregatorPosition.Member);
	}

	/**
	 * @return bundle
	 */
	protected ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * @param model
	 * @param axis
	 * @param position
	 */
	protected DualListModel<SelectItem> createSelectionModel(Axis axis,
			AggregatorPosition position) {
		List<String> selected = renderer.getAggregators(axis, position);
		List<String> available = renderer.getAggregatorFactory()
				.getAvailableAggregations();

		List<SelectItem> unselectedItems = new ArrayList<SelectItem>(
				available.size());
		List<SelectItem> selectedItems = new ArrayList<SelectItem>(
				available.size());

		for (String name : available) {
			String key = "label.aggregation.type." + name;
			String label = bundle.getString(key);

			SelectItem item = new SelectItem(name, label);

			if (selected.contains(name)) {
				selectedItems.add(item);
			} else {
				unselectedItems.add(item);
			}
		}

		return new DualListModel<SelectItem>(unselectedItems, selectedItems);
	}

	/**
	 * @param selection
	 * @param axis
	 * @param position
	 * @return
	 */
	protected void applySelection(DualListModel<SelectItem> selection,
			Axis axis, AggregatorPosition position) {
		List<SelectItem> items = selection.getTarget();

		List<String> aggregators = new ArrayList<String>(items.size());

		for (SelectItem item : items) {
			aggregators.add((String) item.getValue());
		}

		renderer.setAggregators(axis, position, aggregators);
	}

	public void apply() {
		applySelection(rowAggregators, Axis.ROWS, AggregatorPosition.Grand);
		applySelection(rowHierarchyAggregators, Axis.ROWS,
				AggregatorPosition.Hierarchy);
		applySelection(rowMemberAggregators, Axis.ROWS,
				AggregatorPosition.Member);

		applySelection(columnAggregators, Axis.COLUMNS,
				AggregatorPosition.Grand);
		applySelection(columnHierarchyAggregators, Axis.COLUMNS,
				AggregatorPosition.Hierarchy);
		applySelection(columnMemberAggregators, Axis.COLUMNS,
				AggregatorPosition.Member);
	}

	/**
	 * @return the renderer
	 */
	public PrimeFacesPivotRenderer getRenderer() {
		return renderer;
	}

	/**
	 * @param renderer
	 *            the renderer to set
	 */
	public void setRenderer(PrimeFacesPivotRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * @return the columnAggregators
	 */
	public DualListModel<SelectItem> getColumnAggregators() {
		return columnAggregators;
	}

	/**
	 * @param columnAggregators
	 *            the columnAggregators to set
	 */
	public void setColumnAggregators(DualListModel<SelectItem> columnAggregators) {
		this.columnAggregators = columnAggregators;
	}

	/**
	 * @return the columnHierarchyAggregators
	 */
	public DualListModel<SelectItem> getColumnHierarchyAggregators() {
		return columnHierarchyAggregators;
	}

	/**
	 * @param columnHierarchyAggregators
	 *            the columnHierarchyAggregators to set
	 */
	public void setColumnHierarchyAggregators(
			DualListModel<SelectItem> columnHierarchyAggregators) {
		this.columnHierarchyAggregators = columnHierarchyAggregators;
	}

	/**
	 * @return the columnMemberAggregators
	 */
	public DualListModel<SelectItem> getColumnMemberAggregators() {
		return columnMemberAggregators;
	}

	/**
	 * @param columnMemberAggregators
	 *            the columnMemberAggregators to set
	 */
	public void setColumnMemberAggregators(
			DualListModel<SelectItem> columnMemberAggregators) {
		this.columnMemberAggregators = columnMemberAggregators;
	}

	/**
	 * @return the rowAggregators
	 */
	public DualListModel<SelectItem> getRowAggregators() {
		return rowAggregators;
	}

	/**
	 * @param rowAggregators
	 *            the rowAggregators to set
	 */
	public void setRowAggregators(DualListModel<SelectItem> rowAggregators) {
		this.rowAggregators = rowAggregators;
	}

	/**
	 * @return the rowHierarchyAggregators
	 */
	public DualListModel<SelectItem> getRowHierarchyAggregators() {
		return rowHierarchyAggregators;
	}

	/**
	 * @param rowHierarchyAggregators
	 *            the rowHierarchyAggregators to set
	 */
	public void setRowHierarchyAggregators(
			DualListModel<SelectItem> rowHierarchyAggregators) {
		this.rowHierarchyAggregators = rowHierarchyAggregators;
	}

	/**
	 * @return the rowMemberAggregators
	 */
	public DualListModel<SelectItem> getRowMemberAggregators() {
		return rowMemberAggregators;
	}

	/**
	 * @param rowMemberAggregators
	 *            the rowMemberAggregators to set
	 */
	public void setRowMemberAggregators(
			DualListModel<SelectItem> rowMemberAggregators) {
		this.rowMemberAggregators = rowMemberAggregators;
	}
}
