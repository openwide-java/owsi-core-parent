package fr.openwide.core.imports.table.common.mapping.column.builder.state;

import com.google.common.base.Function;

import fr.openwide.core.commons.util.functional.builder.function.generic.GenericBooleanFunctionBuildStateImpl;
import fr.openwide.core.imports.table.common.mapping.AbstractTableImportColumnSet;

public abstract class BooleanState<TTable, TRow, TCell, TCellReference> extends GenericBooleanFunctionBuildStateImpl
		<
		AbstractTableImportColumnSet<TTable, TRow, TCell, TCellReference>.Column<Boolean>,
		ColumnFunctionBuildStateSwitcher<TTable, TRow, TCell, TCellReference, Boolean>,
		BooleanState<TTable, TRow, TCell, TCellReference>,
		DateState<TTable, TRow, TCell, TCellReference>,
		IntegerState<TTable, TRow, TCell, TCellReference>,
		LongState<TTable, TRow, TCell, TCellReference>,
		DoubleState<TTable, TRow, TCell, TCellReference>,
		BigDecimalState<TTable, TRow, TCell, TCellReference>,
		StringState<TTable, TRow, TCell, TCellReference>
		>
		implements ColumnFunctionBuildState<TTable, TRow, TCell, TCellReference, Boolean> {
	
	@Override
	public <TValue> GenericState<TTable, TRow, TCell, TCellReference, TValue> transform(Function<? super Boolean, TValue> function) {
		return getStateSwitcher().toGeneric(function);
	}

}
