package fr.openwide.core.jpa.more.business.difference.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.bindgen.Binding;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.path.NodePath;
import de.danielbechler.diff.path.NodePath.AppendableBuilder;
import de.danielbechler.diff.selector.CollectionItemElementSelector;
import de.danielbechler.diff.selector.ElementSelector;
import de.danielbechler.diff.selector.MapKeyElementSelector;
import de.danielbechler.diff.selector.RootElementSelector;
import fr.openwide.core.commons.util.fieldpath.FieldPath;
import fr.openwide.core.commons.util.fieldpath.FieldPathComponent;
import fr.openwide.core.commons.util.fieldpath.FieldPathPropertyComponent;
import fr.openwide.core.jpa.more.business.difference.model.Difference;
import fr.openwide.core.jpa.more.business.difference.selector.IKeyAwareSelector;

public final class DiffUtils {
	
	private DiffUtils() {
	}
	
	public static ObjectDifferBuilder builder() {
		return ObjectDifferBuilder.startBuilding();
	}
	
	public static <T> Difference<T> diff(T before, T after, ObjectDiffer differ) {
		return new Difference<T>(
				before, after,
				differ.compare(after, before)
		);
	}
	
	public static Predicate<DiffNode> hasPropertyName(String propertyName) {
		return new PropertyNamePredicate(propertyName);
	}
	
	private static class PropertyNamePredicate implements Predicate<DiffNode>, Serializable {
		private static final long serialVersionUID = -3331762514876209810L;
		
		private final String propertyName;
		
		public PropertyNamePredicate(String propertyName) {
			super();
			this.propertyName = propertyName;
		}
		
		@Override
		public boolean apply(DiffNode input) {
			return input != null && propertyName.equals(input.getPropertyName());
		}
	}
	
	public static Predicate<DiffNode> hasPath(FieldPath path) {
		return new FieldPathPredicate(path);
	}
	
	private static class FieldPathPredicate implements Predicate<DiffNode>, Serializable {
		private static final long serialVersionUID = -3331762514876209810L;
		
		private final FieldPath fieldPath;
		
		public FieldPathPredicate(FieldPath fieldPath) {
			super();
			this.fieldPath = fieldPath;
		}
		
		@Override
		public boolean apply(DiffNode input) {
			return input != null && fieldPath.equals(DiffUtils.toFieldPath(input.getPath()));
		}
	}
	
	public static Predicate<DiffNode> isField(Binding<?> binding) {
		return Predicates.and(
				new RootTypePredicate(binding.getRootBinding().getType()),
				new FieldPathPredicate(FieldPath.fromBinding(binding))
		);
	}
	
	private static class RootTypePredicate implements Predicate<DiffNode>, Serializable {
		private static final long serialVersionUID = -3331762514876209810L;
		
		private final Class<?> expectedType;
		
		public RootTypePredicate(Class<?> expectedType) {
			super();
			this.expectedType = expectedType;
		}
		
		@Override
		public boolean apply(DiffNode input) {
			if (input == null) {
				return false;
			}
			DiffNode root =  getRootNode(input);
			return expectedType.isAssignableFrom(root.getValueType());
		}
	}

	public static boolean isItem(DiffNode node) {
		return FieldPathComponent.ITEM.equals(toFieldPathComponent(node.getElementSelector())); 
	}
	
	public static DiffNode getRootNode(DiffNode node) {
		DiffNode parent = node;
		DiffNode currentNode;
		do {
			currentNode = parent;
			parent = currentNode.getParentNode();
		} while (parent != null);
		return currentNode;
	}

	public static FieldPathComponent toFieldPathComponent(ElementSelector elementSelector) {
		if (elementSelector instanceof RootElementSelector) {
			return null;
		} else if (elementSelector instanceof CollectionItemElementSelector) {
			return FieldPathComponent.ITEM;
		} else if (elementSelector instanceof MapKeyElementSelector) {
			return FieldPathComponent.ITEM;
		} else if (elementSelector instanceof IKeyAwareSelector<?>) {
			return FieldPathComponent.ITEM;
		} else {
			return new FieldPathPropertyComponent(elementSelector.toString());
		}
	}

	public static FieldPath toFieldPath(NodePath nodePath) {
		final Iterator<ElementSelector> iterator = nodePath.getElementSelectors().iterator();
		
		List<FieldPathComponent> components = Lists.newArrayList();
		while (iterator.hasNext()) {
			final ElementSelector elementSelector = iterator.next();
			final FieldPathComponent component = toFieldPathComponent(elementSelector);
			if (component != null) {
				components.add(component);
			}
		}
		
		return FieldPath.of(components);
	}

	public static NodePath toNodePath(Binding<?> binding) {
		return toNodePath(FieldPath.fromBinding(binding));
	}

	public static NodePath toNodePath(FieldPath fieldPath) {
		final Iterator<FieldPathComponent> iterator = fieldPath.iterator();
		
		AppendableBuilder builder = NodePath.startBuilding();
		while (iterator.hasNext()) {
			final FieldPathComponent pathComponent = iterator.next();
			if (FieldPathComponent.ITEM.equals(pathComponent)) {
				throw new IllegalArgumentException("Transforming FieldPaths containing item path components to NodePath is not supported.");
			}
			builder = builder.propertyName(pathComponent.getName());
		}
		
		return builder.build();
	}
	
	public static Function<FieldPath, NodePath> toNodePathFunction() {
		return ToNodePath.INSTANCE;
	}
	
	private static enum ToNodePath implements Function<FieldPath, NodePath> {
		INSTANCE;
		
		@Override
		public NodePath apply(@Nonnull FieldPath input) {
			return DiffUtils.toNodePath(input);
		}
		
		@Override
		public String toString() {
			return "ToNodePath.INSTANCE";
		}
	};
}
