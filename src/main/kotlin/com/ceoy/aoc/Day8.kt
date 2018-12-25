package com.ceoy.aoc

class Day8(input: List<String> = FileLoader.load("day8.txt")) {

    private val input = input
            .last()
            .split(" ")
            .map {
                it.toInt()
            }

    fun partOne(): Int {
        val tree = Node.parse(input)

        return tree.sumMetaData()
    }

    fun partTwo(): Int {
        val tree = Node.parse(input)

        return tree.complicatedMathByMe()
    }

    /**
     * The Node of the Tree
     */
    data class Node(val header: Header,
                    val childNodes: MutableList<Node>,
                    val metaData: MutableList<Int>) {
        companion object {

            /**
             * Parse the Node for the Tree
             * @param nodeInput the input list
             * @param takeFrom define a start from the array
             */
            fun parse(nodeInput: List<Int>, takeFrom: Int = 0): Node {
                // get first two for header
                val (indexOne, indexTwo) = Pair(takeFrom, takeFrom + 1)
                val header = Header(nodeInput[indexOne], nodeInput[indexTwo])
                var cont = takeFrom + 2

                val nodes = mutableListOf<Node>()
                for (i in 0 until header.childNodes) {
                    val node = Node.parse(nodeInput, cont)
                    // add the new amount of child nodes and meta data
                    nodes.add(node)

                    cont = takeFrom + nodes.sumBy {
                        it.countSteps()
                    } + 2
                }

                // add the meta data
                val metaData = mutableListOf<Int>()
                for (i in 0 until header.metaData) {
                    metaData.add(nodeInput[cont + i])
                }

                return Node(header, nodes, metaData)
            }
        }

        /**
         * Sums the MetaData
         */
        fun sumMetaData(): Int {
            return metaData.sum() + childNodes.sumBy {
                it.sumMetaData()
            }
        }

        private fun countSteps(): Int {
            // metadata = amount of meta data, 2 = size of header
            return  (header.metaData + 2
                    // do the same with all children
                    + childNodes.sumBy { it.countSteps() })
        }

        fun complicatedMathByMe(): Int {
            return getValue()
        }

        private fun getValue(): Int {
            if (this.childNodes.size == 0) {
                return metaData.sum()
            }

            return metaData.sumBy {
                this.childNodes.getOrNull(it - 1)?.getValue() ?: 0
            }
        }
    }

    /**
     * The Header of a Node
     */
    data class Header(val childNodes: Int,
                      val metaData: Int)

}