

public class BinarySearchTreeOfInteger {
    
    
    // Classe interna privada
    private static final class Node {
        public Integer element;
        public Node father;
        public Node left;
        public Node right;
        public Node(Integer element) {
            father = null;
            left = null;
            right = null;
            this.element = element;
        }
    }

    
    // Atributos        
    private int count; //contador do numero de nodos
    private Node root; //referencia para o nodo raiz


    // Metodos
    public BinarySearchTreeOfInteger() {
        count = 0;
        root = null;
    }

    public void clear() {
        count = 0;
        root = null;      
    }
    
    public boolean isEmpty() {
        return (root == null);
    }

    public int size() {
        return count;
    }

    private Node searchNodeRef(Integer element, Node target) {
        int r;

        if (element == null || target == null) {
            return null;
        }

        r = target.element.compareTo(element);

        if (r == 0) {
            return target;
        } else if (r > 0) {
            return searchNodeRef(element, target.left);
        } else {
            return searchNodeRef(element, target.right);
        }
    }      

    public Integer getRoot() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root.element;
    }
    
    public Integer getLeft(Integer element) {
        Integer res = null;
        Node nAux = searchNodeRef(element, root);
        if (nAux != null) {
            if (nAux.left != null) {
                res = nAux.left.element;
            }
        }
        return res;
    }

    public boolean contains(Integer element) {
        Node nAux = searchNodeRef(element, root);
        return (nAux != null);
    }

    public boolean isRoot(Integer element) {
        if (root != null) {
            if (root.element.equals(element)) {
                return true;
            }
        }
        return false;
    }    
    
    /**
     * Conta o total de nodos da subarvore cuja raiz esta sendo passada por 
     * parametro.
     * @param n referencia para o nodo a partir do qual sera feita a contagem
     * @return total de elementos da subarvore
     */
    private int countNodes(Node n) {
        if (n == null) {
            return 0;
        } else {
            return 1 + countNodes(n.left) + countNodes(n.right);
        }
    }  
    
    /**
     * Retorna uma lista com todos os elementos da arvore. Os elementos
     * sao colocados na lista seguindo um caminhamento prefixado.
     * @return lista com os elementos da arvore na ordem prefixada
     */
    public LinkedListOfInteger positionsPre() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPreAux(root, res);
        return res;
    }
    private void positionsPreAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            res.add(n.element); //Visita o nodo
            positionsPreAux(n.left, res); //Visita a subarvore esquerda
            positionsPreAux(n.right, res); //Visita a subarvore direita
        }
    }

    /**
     * Retorna uma lista com todos os elementos da arvore. Os elementos
     * sao colocados na lista seguindo um caminhamento central.
     * @return lista com os elementos da arvore na ordem central
     */    
    public LinkedListOfInteger positionsCentral() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsCentralAux(root, res);
        return res;
    }
    private void positionsCentralAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsCentralAux(n.left, res); //Visita a subarvore esquerda
            res.add(n.element); //Visita o nodo
            positionsCentralAux(n.right, res); //Visita a subarvore direita
        }
    }

    /**
     * Procura pelo menor elemento da subarvore cuja raiz eh passada por
     * parametro,e retorna a referencia para o nodo no qual este elemento
     * esta armazenado.
     * @param n referencia para a raiz da subarvore na qual deve ser 
     * feita a busca.
     * @return referencia para o Node que contem o menor elemento da
     * subarvore.
     */
    private Node smallest(Node n) {
        if (n == null) {
            return null;
        }
        if (n.left == null) {
            return n;
        }
        return smallest(n.left);
    }
    
    
    
    ////////////////////////////////////////////////////
    // Implemente os métodos abaixo
    ////////////////////////////////////////////////////
    
    /**
     * Adiciona o elemento passado por parametro na arvore. 
     * @param element elemento a ser adicionado na arvore.
     */
    public void add(Integer element) {
        root = add(root, element, null);
        count++;
    }
    private Node add(Node n, Integer e, Node father) {
        if (n == null) {
            Node aux = new Node(e);
            aux.father = father;
            return aux;
        }

        if (e<n.element) {
            n.left = add(n.left, e, n);
        } else {
            n.right = add(n.right, e, n);
        }
        return n;
    }   
    
    /**
     * Remove da arvore o elemento passado como parametro, mantendo as 
     * propriedades da ABP.
     * @param element elemento a ser removido.
     * @return true se achou o elemento e fez a remocao, e false caso 
     * contrario.
     */
    public boolean remove(Integer element) {
        if (root == null || element == null) {
            return false;
        }

        Node aux = searchNodeRef(element, root);
        if (aux == null) {
            return false;
        }
        remove(element);
        count--;
        return true;
    }

    private void remove(Node n) {
        //Remoção de folha
        if (n.left == null && n.right == null) {

            if (root == n) {
                root = null;
            } else {
                Node pai = n.father;
                if (pai.left == n) {
                    pai.left = null;
                } else {
                    pai.right = null;
                }
            }

        } else if(n.left == null && n.right != null) { // Remoção de nodo com um filho a direita

            if (root == n) {
                root = null;
            } else {
                Node pai = n.father;
                if (pai.left == n) {
                    pai.left = n.right;
                } else {
                    pai.right = n.right;
                }
            }

        } else if (n.left != null && n.right == null) { // Remoção de nodo com um filho a esquerda

        } else {
            // remoção de nodo com dois filhos
            Node s = smallest(n.right); // Verifica o menor do lado direito
            n.element = s.element;
            remove(s);
        }
    }
 
    /** 
     * Retorna uma lista com todos os elementos da arvore na ordem de 
     * caminhamento em largura. 
     * @return LinkedListOfInteger lista com os elementos da arvore
     */     
    public LinkedListOfInteger positionsWidth() {
        Queue<Node> fila = new Queue<>();
        LinkedListOfInteger res = new LinkedListOfInteger();
        // Implemente este metodo 
        return res;
    }    
    
    /**
     * Retorna o elemento que eh o filho a direita do elemento 
     * passado por parametro.
     * @param element do qual se quer saber quem eh o filho a direita.
     * @return fiho da direita do elemento passado por parametro.
     */    
    public Integer getRight(Integer element) {
        // Implemente este metodo 
        return 0;
    }

    /**
     * Retorna o elemento que eh o pai do elemento passado por
     * parametro.
     * @param element do qual se quer saber quem eh o pai.
     * @return pai do elemento passado por parametro.
     */
    public Integer getParent(Integer element) {
        // Implemente este metodo         
        return 0;
    }    

    /**
     * Remove um galho da árvore. A raiz deste galho eh o nodo que contem 
     * o elemento passado por parâmetro (element). Caso "element" nao esteja
     * na arvore, nao eh feita a remocao e o metodo retorna false.
     * @param element raiz da subarvore que deve ser removida
     * @return true se houve a remocao do galho, false caso contrario.
     */
    public boolean removeBranch(Integer element) {
        // Implemente este metodo
        return false;
    }
    
    /**
     * Retorna a altura da arvore. Deve chamar um metodo auxiliar recursivo.
     * @return altura da arvore
     */    
    public int height() {        
        //Implemente este metodo (de preferencia de forma recursiva)
        return 0;
    }
    
    /**
     * Retorna o nivel do nodo no qual esta armazenado o elemento
     * passadado por parametro.
     * @param element o elemento que se quer saber o nivel.
     * @return o nivel do nodo onde esta o elemento, ou -1 se nao
     * encontrou o elemento.
     */
    public int level(Integer element) {
        //Implemente este metodo
        return 0;
    }    
    
    /** 
     * Retorna uma string que contem todos os elementos da arvore na ordem de 
     * caminhamento pre-fixada. Chamar um metodo auxiliar recursivo.
     * @return String com todos os elementos da arvore
     */        
    public String strTraversalPre() {
        return strTraversalPre(root);
    }
    private String strTraversalPre(Node n) {
        String res = "";
        // Implemente este metodo
        return res;
    }

    /** 
     * Retorna uma string que contem todos os elementos da arvore na ordem de 
     * caminhamento pos-fixada. Chamar um metodo auxiliar recursivo.
     * @return String com todos os elementos da arvore
     */       
    public String strTraversalPos() {
        return strTraversalPos(root);
    }
    private String strTraversalPos(Node n) {
        String res = "";
        // Implemente este metodo
        return res;
    }

    /** 
     * Retorna uma string que contem todos os elementos da arvore na ordem de 
     * caminhamento central. Chamar um metodo auxiliar recursivo.
     * @return String com todos os elementos da arvore
     */       
    public String strTraversalCentral() {
        return strTraversalCentral(root);
    }
    private String strTraversalCentral(Node n) {
        String res = "";
        // Implemente este metodo
        return res;
    }    
}
