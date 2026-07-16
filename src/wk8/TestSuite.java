/*
 * Course: CSC-1120
 * ASSIGNMENT
 * CLASS
 * Name: Sean Jones
 * Last Updated:
 */
package wk8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * Tests the structure and public behavior of {@link BinaryTree}.
 */
@DisplayName("BinaryTree")
public class TestSuite {

    @Nested
    @DisplayName("Node class")
    class NodeTests {

        @Test
        @DisplayName("Node has the required class structure")
        void nodeClassStructureTest() {
            Class<?> nodeClass = getNodeClass();

            Assertions.assertTrue(Modifier.isProtected(nodeClass.getModifiers()),
                    "Node must be protected");
            Assertions.assertTrue(Modifier.isStatic(nodeClass.getModifiers()),
                    "Node must be static");
            Assertions.assertFalse(Modifier.isAbstract(nodeClass.getModifiers()),
                    "Node must not be abstract");
            Assertions.assertEquals(List.of("E"), typeParameterNames(nodeClass),
                    "Node must declare one generic type parameter named E");
        }

        @Test
        @DisplayName("Node declares protected data, left, and right fields")
        void nodeFieldStructureTest() throws ReflectiveOperationException {
            Class<?> nodeClass = getNodeClass();
            Field dataField = nodeClass.getDeclaredField("data");
            Field leftField = nodeClass.getDeclaredField("left");
            Field rightField = nodeClass.getDeclaredField("right");

            assertProtectedInstanceField(dataField);
            assertProtectedInstanceField(leftField);
            assertProtectedInstanceField(rightField);

            Assertions.assertEquals("E", dataField.getGenericType().getTypeName(),
                    "data must use the Node generic type");
            assertNodeReferenceField(leftField, nodeClass);
            assertNodeReferenceField(rightField, nodeClass);
        }

        @Test
        @DisplayName("Node constructor stores data and initializes null children")
        void nodeClassConstructorTest() throws ReflectiveOperationException {
            final String expectedData = "root";
            Object node = newNode(expectedData);

            Assertions.assertEquals(expectedData, readNodeField(node, "data"));
            Assertions.assertNull(readNodeField(node, "left"));
            Assertions.assertNull(readNodeField(node, "right"));
        }

        @Test
        @DisplayName("Node constructor is protected")
        void nodeConstructorStructureTest() throws ReflectiveOperationException {
            Constructor<?> constructor = getNodeClass().getDeclaredConstructor(Object.class);

            Assertions.assertTrue(Modifier.isProtected(constructor.getModifiers()),
                    "Node(E) must be protected");
        }

        @Test
        @DisplayName("Node toString returns its data as text")
        void nodeToStringTest() throws ReflectiveOperationException {
            final String expectedText = "node-data";
            Object node = newNode(expectedText);

            Assertions.assertEquals(expectedText, node.toString());
        }

        @Test
        @DisplayName("Node toString handles null data")
        void nodeToStringHandlesNullDataTest() throws ReflectiveOperationException {
            final String expectedText = "null";
            Object node = newNode(null);

            Assertions.assertEquals(expectedText, node.toString());
        }

        @Test
        @DisplayName("Node overrides toString publicly")
        void nodeToStringStructureTest() throws ReflectiveOperationException {
            Method method = getNodeClass().getDeclaredMethod("toString");

            Assertions.assertTrue(Modifier.isPublic(method.getModifiers()),
                    "Node.toString must be public");
            Assertions.assertEquals(String.class, method.getReturnType());
        }
    }

    @Nested
    @DisplayName("BinaryTree class structure")
    class BinaryTreeStructureTests {

        @Test
        @DisplayName("BinaryTree has the required class structure")
        void binaryTreeClassStructureTest() {
            Class<?> treeClass = BinaryTree.class;

            Assertions.assertTrue(Modifier.isPublic(treeClass.getModifiers()),
                    "BinaryTree must be public");
            Assertions.assertFalse(Modifier.isAbstract(treeClass.getModifiers()),
                    "BinaryTree must not be abstract");
            Assertions.assertEquals(List.of("E"), typeParameterNames(treeClass),
                    "BinaryTree must declare one generic type parameter named E");
        }

        @Test
        @DisplayName("BinaryTree declares a protected root field")
        void rootFieldStructureTest() throws ReflectiveOperationException {
            Field rootField = BinaryTree.class.getDeclaredField("root");

            assertProtectedInstanceField(rootField);
            assertNodeReferenceField(rootField, getNodeClass());
        }

        @Test
        @DisplayName("BinaryTree declares the required constructors")
        void constructorStructureTest() throws ReflectiveOperationException {
            Constructor<?> emptyConstructor = BinaryTree.class.getConstructor();
            Constructor<?> subtreeConstructor = BinaryTree.class.getConstructor(
                    Object.class, BinaryTree.class, BinaryTree.class);
            Constructor<?> nodeConstructor = BinaryTree.class.getDeclaredConstructor(
                    getNodeClass());

            Assertions.assertTrue(Modifier.isPublic(emptyConstructor.getModifiers()),
                    "BinaryTree() must be public");
            Assertions.assertTrue(Modifier.isPublic(subtreeConstructor.getModifiers()),
                    "BinaryTree(E, BinaryTree<E>, BinaryTree<E>) must be public");
            Assertions.assertTrue(Modifier.isProtected(nodeConstructor.getModifiers()),
                    "BinaryTree(Node<E>) must be protected");
        }

        @Test
        @DisplayName("BinaryTree declares the required public instance methods")
        void publicInstanceMethodStructureTest() throws ReflectiveOperationException {
            assertPublicInstanceMethod("getLeftSubTree", BinaryTree.class);
            assertPublicInstanceMethod("getRightSubTree", BinaryTree.class);
            assertPublicInstanceMethod("getData", Object.class);
            assertPublicInstanceMethod("isLeaf", boolean.class);
            assertPublicInstanceMethod("toString", String.class);
            assertPublicInstanceMethod("toStringOld", String.class);
            assertPublicInstanceMethod("preOrderTraversal", void.class, BiConsumer.class);
            assertPublicInstanceMethod("inOrderTraversal", void.class, BiConsumer.class);
            assertPublicInstanceMethod("postOrderTraversal", void.class, BiConsumer.class);
        }

        @Test
        @DisplayName("readBinaryTree is a public static method")
        void readBinaryTreeMethodStructureTest() throws ReflectiveOperationException {
            Method method = BinaryTree.class.getDeclaredMethod("readBinaryTree", Scanner.class);

            Assertions.assertTrue(Modifier.isPublic(method.getModifiers()),
                    "readBinaryTree must be public");
            Assertions.assertTrue(Modifier.isStatic(method.getModifiers()),
                    "readBinaryTree must be static");
            Assertions.assertEquals(BinaryTree.class, method.getReturnType());
        }
    }

    @Nested
    @DisplayName("Constructors")
    class ConstructorTests {

        @Test
        @DisplayName("No-argument constructor creates an empty tree")
        void noArgumentConstructorCreatesEmptyTreeTest()
                throws ReflectiveOperationException {
            BinaryTree<String> tree = new BinaryTree<>();

            Assertions.assertNull(readRoot(tree));
            Assertions.assertNull(tree.getData());
        }

        @Test
        @DisplayName("Public constructor stores root data and both subtrees")
        void publicConstructorStoresRootAndSubtreesTest()
                throws ReflectiveOperationException {
            final String rootData = "A";
            final String leftData = "B";
            final String rightData = "C";
            BinaryTree<String> leftTree = leaf(leftData);
            BinaryTree<String> rightTree = leaf(rightData);
            Object expectedLeftRoot = readRoot(leftTree);
            Object expectedRightRoot = readRoot(rightTree);

            BinaryTree<String> tree = new BinaryTree<>(rootData, leftTree, rightTree);
            Object root = readRoot(tree);

            Assertions.assertEquals(rootData, tree.getData());
            Assertions.assertSame(expectedLeftRoot, readNodeField(root, "left"),
                    "The constructor must link the supplied left subtree root");
            Assertions.assertSame(expectedRightRoot, readNodeField(root, "right"),
                    "The constructor must link the supplied right subtree root");
        }

        @Test
        @DisplayName("Public constructor accepts null subtrees")
        void publicConstructorAcceptsNullSubtreesTest()
                throws ReflectiveOperationException {
            final String rootData = "A";
            BinaryTree<String> tree = new BinaryTree<>(rootData, null, null);
            Object root = readRoot(tree);

            Assertions.assertEquals(rootData, tree.getData());
            Assertions.assertNull(readNodeField(root, "left"));
            Assertions.assertNull(readNodeField(root, "right"));
        }

        @Test
        @DisplayName("Protected constructor stores the supplied node")
        void protectedConstructorStoresProvidedNodeTest()
                throws ReflectiveOperationException {
            final String rootData = "A";
            Object expectedRoot = newNode(rootData);
            Constructor<?> constructor = BinaryTree.class.getDeclaredConstructor(
                    getNodeClass());
            constructor.setAccessible(true);

            BinaryTree<?> tree = (BinaryTree<?>) constructor.newInstance(expectedRoot);

            Assertions.assertSame(expectedRoot, readRoot(tree));
            Assertions.assertEquals(rootData, tree.getData());
        }
    }

    @Nested
    @DisplayName("Data and subtree accessors")
    class AccessorTests {

        @Test
        @DisplayName("getData returns null for an empty tree")
        void getDataReturnsNullForEmptyTreeTest() {
            BinaryTree<String> tree = new BinaryTree<>();

            Assertions.assertNull(tree.getData());
        }

        @Test
        @DisplayName("getData returns the root data")
        void getDataReturnsRootDataTest() {
            final String expectedData = "root";
            BinaryTree<String> tree = leaf(expectedData);

            Assertions.assertEquals(expectedData, tree.getData());
        }

        @Test
        @DisplayName("Subtree accessors return null for an empty tree")
        void subtreeAccessorsReturnNullForEmptyTreeTest() {
            BinaryTree<String> tree = new BinaryTree<>();

            Assertions.assertNull(tree.getLeftSubTree());
            Assertions.assertNull(tree.getRightSubTree());
        }

        @Test
        @DisplayName("Subtree accessors return null for a leaf")
        void subtreeAccessorsReturnNullForLeafTest() {
            BinaryTree<String> tree = leaf("A");

            Assertions.assertNull(tree.getLeftSubTree());
            Assertions.assertNull(tree.getRightSubTree());
        }

        @Test
        @DisplayName("Subtree accessors return the expected child data")
        void subtreeAccessorsReturnExpectedDataTest() {
            final String leftData = "B";
            final String rightData = "C";
            BinaryTree<String> tree = new BinaryTree<>("A",
                    leaf(leftData), leaf(rightData));

            BinaryTree<String> leftSubtree = tree.getLeftSubTree();
            BinaryTree<String> rightSubtree = tree.getRightSubTree();

            Assertions.assertNotNull(leftSubtree);
            Assertions.assertNotNull(rightSubtree);
            Assertions.assertEquals(leftData, leftSubtree.getData());
            Assertions.assertEquals(rightData, rightSubtree.getData());
        }

        @Test
        @DisplayName("Subtree accessors wrap the original child nodes")
        void subtreeAccessorsWrapOriginalNodesTest()
                throws ReflectiveOperationException {
            BinaryTree<String> leftTree = leaf("B");
            BinaryTree<String> rightTree = leaf("C");
            BinaryTree<String> tree = new BinaryTree<>("A", leftTree, rightTree);

            BinaryTree<String> leftSubtree = tree.getLeftSubTree();
            BinaryTree<String> rightSubtree = tree.getRightSubTree();

            Assertions.assertNotNull(leftSubtree);
            Assertions.assertNotNull(rightSubtree);
            Assertions.assertSame(readRoot(leftTree), readRoot(leftSubtree));
            Assertions.assertSame(readRoot(rightTree), readRoot(rightSubtree));
        }
    }

    @Nested
    @DisplayName("Leaf detection")
    class LeafTests {

        @Test
        @DisplayName("An empty tree is not a leaf")
        void emptyTreeIsNotLeafTest() {
            BinaryTree<String> tree = new BinaryTree<>();

            Assertions.assertFalse(tree.isLeaf());
        }

        @Test
        @DisplayName("A node with no children is a leaf")
        void childlessNodeIsLeafTest() {
            BinaryTree<String> tree = leaf("A");

            Assertions.assertTrue(tree.isLeaf());
        }

        @Test
        @DisplayName("A node with a left child is not a leaf")
        void nodeWithLeftChildIsNotLeafTest() {
            BinaryTree<String> tree = new BinaryTree<>("A", leaf("B"), null);

            Assertions.assertFalse(tree.isLeaf());
        }

        @Test
        @DisplayName("A node with a right child is not a leaf")
        void nodeWithRightChildIsNotLeafTest() {
            BinaryTree<String> tree = new BinaryTree<>("A", null, leaf("C"));

            Assertions.assertFalse(tree.isLeaf());
        }

        @Test
        @DisplayName("A leaf may store null data")
        void leafMayStoreNullDataTest() {
            BinaryTree<String> tree = new BinaryTree<>(null, null, null);

            Assertions.assertTrue(tree.isLeaf());
            Assertions.assertNull(tree.getData());
        }
    }

    @Nested
    @DisplayName("Traversals")
    class TraversalTests {

        @Test
        @DisplayName("Traversals do not call the consumer for an empty tree")
        void emptyTreeTraversalTest() {
            BinaryTree<String> tree = new BinaryTree<>();
            List<Visit<String>> visits = new ArrayList<>();

            tree.preOrderTraversal((data, depth) -> visits.add(new Visit<>(data, depth)));
            tree.inOrderTraversal((data, depth) -> visits.add(new Visit<>(data, depth)));
            tree.postOrderTraversal((data, depth) -> visits.add(new Visit<>(data, depth)));

            Assertions.assertTrue(visits.isEmpty());
        }

        @Test
        @DisplayName("Every traversal reports the root at depth one")
        void traversalRootDepthTest() {
            final int rootDepth = 1;
            final String rootData = "A";
            BinaryTree<String> tree = leaf(rootData);
            List<Visit<String>> expected = List.of(new Visit<>(rootData, rootDepth));
            List<Visit<String>> preOrder = new ArrayList<>();
            List<Visit<String>> inOrder = new ArrayList<>();
            List<Visit<String>> postOrder = new ArrayList<>();

            tree.preOrderTraversal((data, depth) ->
                    preOrder.add(new Visit<>(data, depth)));
            tree.inOrderTraversal((data, depth) ->
                    inOrder.add(new Visit<>(data, depth)));
            tree.postOrderTraversal((data, depth) ->
                    postOrder.add(new Visit<>(data, depth)));

            Assertions.assertEquals(expected, preOrder);
            Assertions.assertEquals(expected, inOrder);
            Assertions.assertEquals(expected, postOrder);
        }

        @Test
        @DisplayName("Pre-order traversal visits root, left, then right")
        void preOrderTraversalTest() {
            final int rootDepth = 1;
            final int childDepth = rootDepth + 1;
            final int grandchildDepth = childDepth + 1;
            BinaryTree<String> tree = traversalTree();
            List<Visit<String>> expected = List.of(
                    new Visit<>("A", rootDepth),
                    new Visit<>("B", childDepth),
                    new Visit<>("D", grandchildDepth),
                    new Visit<>("E", grandchildDepth),
                    new Visit<>("C", childDepth),
                    new Visit<>("F", grandchildDepth));
            List<Visit<String>> actual = new ArrayList<>();

            tree.preOrderTraversal((data, depth) ->
                    actual.add(new Visit<>(data, depth)));

            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("In-order traversal visits left, root, then right")
        void inOrderTraversalTest() {
            final int rootDepth = 1;
            final int childDepth = rootDepth + 1;
            final int grandchildDepth = childDepth + 1;
            BinaryTree<String> tree = traversalTree();
            List<Visit<String>> expected = List.of(
                    new Visit<>("D", grandchildDepth),
                    new Visit<>("B", childDepth),
                    new Visit<>("E", grandchildDepth),
                    new Visit<>("A", rootDepth),
                    new Visit<>("C", childDepth),
                    new Visit<>("F", grandchildDepth));
            List<Visit<String>> actual = new ArrayList<>();

            tree.inOrderTraversal((data, depth) ->
                    actual.add(new Visit<>(data, depth)));

            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Post-order traversal visits left, right, then root")
        void postOrderTraversalTest() {
            final int rootDepth = 1;
            final int childDepth = rootDepth + 1;
            final int grandchildDepth = childDepth + 1;
            BinaryTree<String> tree = traversalTree();
            List<Visit<String>> expected = List.of(
                    new Visit<>("D", grandchildDepth),
                    new Visit<>("E", grandchildDepth),
                    new Visit<>("B", childDepth),
                    new Visit<>("F", grandchildDepth),
                    new Visit<>("C", childDepth),
                    new Visit<>("A", rootDepth));
            List<Visit<String>> actual = new ArrayList<>();

            tree.postOrderTraversal((data, depth) ->
                    actual.add(new Visit<>(data, depth)));

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("String representations")
    class StringRepresentationTests {

        @Test
        @DisplayName("toString renders an empty tree")
        void toStringRendersEmptyTreeTest() {
            final String expected = "null\n";
            BinaryTree<String> tree = new BinaryTree<>();

            Assertions.assertEquals(expected, tree.toString());
        }

        @Test
        @DisplayName("toString renders a leaf")
        void toStringRendersLeafTest() {
            final String expected = "   A\n";
            BinaryTree<String> tree = leaf("A");

            Assertions.assertEquals(expected, tree.toString());
        }

        @Test
        @DisplayName("toString renders a root with two children")
        void toStringRendersThreeNodeTreeTest() {
            final String expected = """
                          A
                         / \\
                       B     C
                    """;
            BinaryTree<String> tree = new BinaryTree<>("A", leaf("B"), leaf("C"));

            Assertions.assertEquals(expected, tree.toString());
        }

        @Test
        @DisplayName("toStringOld renders an empty tree")
        void toStringOldRendersEmptyTreeTest() {
            final String expected = "null\n";
            BinaryTree<String> tree = new BinaryTree<>();

            Assertions.assertEquals(expected, tree.toStringOld());
        }

        @Test
        @DisplayName("toStringOld uses preorder with explicit null children")
        void toStringOldRendersTreeTest() {
            final String expected = """
                    A
                     B
                      null
                      null
                     C
                      null
                      null
                    """;
            BinaryTree<String> tree = new BinaryTree<>("A", leaf("B"), leaf("C"));

            Assertions.assertEquals(expected, tree.toStringOld());
        }
    }

    @Nested
    @DisplayName("Reading trees")
    class ReadBinaryTreeTests {

        @Test
        @DisplayName("readBinaryTree returns null for null input marker")
        void readBinaryTreeReadsEmptyTreeTest() {
            final String input = "null\n";

            try(Scanner scanner = new Scanner(input)) {
                BinaryTree<String> tree = BinaryTree.readBinaryTree(scanner);

                Assertions.assertNull(tree);
            }
        }

        @Test
        @DisplayName("readBinaryTree reads a leaf")
        void readBinaryTreeReadsLeafTest() {
            final String input = "A\nnull\nnull\n";

            try(Scanner scanner = new Scanner(input)) {
                BinaryTree<String> tree = BinaryTree.readBinaryTree(scanner);

                Assertions.assertNotNull(tree);
                Assertions.assertEquals("A", tree.getData());
                Assertions.assertTrue(tree.isLeaf());
            }
        }

        @Test
        @DisplayName("readBinaryTree reads preorder input recursively")
        void readBinaryTreeReadsCompleteTreeTest() {
            final int rootDepth = 1;
            final int childDepth = rootDepth + 1;
            final String input = "A\nB\nnull\nnull\nC\nnull\nnull\n";
            List<Visit<String>> expected = List.of(
                    new Visit<>("B", childDepth),
                    new Visit<>("A", rootDepth),
                    new Visit<>("C", childDepth));

            try(Scanner scanner = new Scanner(input)) {
                BinaryTree<String> tree = BinaryTree.readBinaryTree(scanner);

                Assertions.assertNotNull(tree);
                Assertions.assertEquals(expected, collectInOrder(tree));
            }
        }

        @Test
        @DisplayName("readBinaryTree trims each input line")
        void readBinaryTreeTrimsInputTest() {
            final String expectedData = "root value";
            final String input = "  root value  \n  null  \n null \n";

            try(Scanner scanner = new Scanner(input)) {
                BinaryTree<String> tree = BinaryTree.readBinaryTree(scanner);

                Assertions.assertNotNull(tree);
                Assertions.assertEquals(expectedData, tree.getData());
                Assertions.assertTrue(tree.isLeaf());
            }
        }
    }

    private record Visit<E>(E data, int depth) {
    }

    private static BinaryTree<String> leaf(String data) {
        return new BinaryTree<>(data, null, null);
    }

    private static BinaryTree<String> traversalTree() {
        BinaryTree<String> left = new BinaryTree<>("B", leaf("D"), leaf("E"));
        BinaryTree<String> right = new BinaryTree<>("C", null, leaf("F"));
        return new BinaryTree<>("A", left, right);
    }

    private static List<Visit<String>> collectInOrder(BinaryTree<String> tree) {
        List<Visit<String>> visits = new ArrayList<>();
        tree.inOrderTraversal((data, depth) -> visits.add(new Visit<>(data, depth)));
        return visits;
    }

    private static Class<?> getNodeClass() {
        return Arrays.stream(BinaryTree.class.getDeclaredClasses())
                .filter(candidate -> candidate.getSimpleName().equals("Node"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("BinaryTree must declare Node"));
    }

    private static List<String> typeParameterNames(Class<?> type) {
        return Arrays.stream(type.getTypeParameters())
                .map(TypeVariable::getName)
                .toList();
    }

    private static void assertProtectedInstanceField(Field field) {
        Assertions.assertTrue(Modifier.isProtected(field.getModifiers()),
                field.getName() + " must be protected");
        Assertions.assertFalse(Modifier.isStatic(field.getModifiers()),
                field.getName() + " must be an instance field");
    }

    private static void assertNodeReferenceField(Field field, Class<?> nodeClass) {
        Assertions.assertEquals(nodeClass, field.getType(),
                field.getName() + " must have Node as its raw type");
        ParameterizedType genericType = Assertions.assertInstanceOf(
                ParameterizedType.class, field.getGenericType(),
                field.getName() + " must retain its generic Node type");
        Assertions.assertEquals(nodeClass, genericType.getRawType());
        List<String> actualTypeArguments = Arrays.stream(genericType.getActualTypeArguments())
                .map(Type::getTypeName)
                .toList();
        Assertions.assertEquals(List.of("E"), actualTypeArguments,
                field.getName() + " must be Node<E>");
    }

    private static void assertPublicInstanceMethod(String name, Class<?> returnType,
                                                   Class<?>... parameterTypes)
            throws ReflectiveOperationException {
        Method method = BinaryTree.class.getDeclaredMethod(name, parameterTypes);

        Assertions.assertTrue(Modifier.isPublic(method.getModifiers()),
                name + " must be public");
        Assertions.assertFalse(Modifier.isStatic(method.getModifiers()),
                name + " must be an instance method");
        Assertions.assertEquals(returnType, method.getReturnType(),
                name + " has the wrong return type");
    }

    private static Object newNode(Object data) throws ReflectiveOperationException {
        Constructor<?> constructor = getNodeClass().getDeclaredConstructor(Object.class);
        constructor.setAccessible(true);
        return constructor.newInstance(data);
    }

    private static Object readRoot(BinaryTree<?> tree) throws ReflectiveOperationException {
        Field rootField = BinaryTree.class.getDeclaredField("root");
        rootField.setAccessible(true);
        return rootField.get(tree);
    }

    private static Object readNodeField(Object node, String fieldName)
            throws ReflectiveOperationException {
        Field field = getNodeClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(node);
    }
}
