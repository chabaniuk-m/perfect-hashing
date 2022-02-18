package hashing.hash;

import hashing.Util;
import hashing.key.Vector;

import java.util.*;

/**
 * function of perfect hashing
 */
public class Perfect {

    /**
     * first-step hash function
     */
    private Universal primary = null;
    private Table lookUpTable = null;

    // MAIN FUNCTION
    public Table buildFor(List<Vector> keys) {

        checkUnique(keys);

        return secondStep(firstStep(keys));
    }

    private void checkUnique(List<Vector> keys) {

        boolean isUnique = true;

        /*int n = keys.size();

        var uniques = new ArrayList<Vector>(n);

        for (int i = 0; i < n; i++) {
            var key = keys.get(i);
            for (int j = 0; j < uniques.size(); j++) {
                var uniq = uniques.get(j);
                if (key.size() == uniq.size()) {
                    System.out.println();
                }
            }
        }*/

        var set = Set.of(keys.toArray(new Vector[]{new Vector(new ArrayList<>())}));

        if (set.size() < keys.size()) {
            System.out.println("=-=-=-=-=--=--=-=-=-=-=-=-=-=-=-=-");
            System.out.println("ERROR: key set contains duplicates");
            System.out.println("=-=-=-=-=--=--=-=-=-=-=-=-=-=-=-=-");
            throw new RuntimeException();
        }
    }

    /**
     * First step of perfect hashing: creates set of buckets
     * @param keys
     * @return
     */
    private List<LinkedList<Vector>> firstStep(List<Vector> keys) {
        int n = keys.size();
        primary = Util.randUHFunction(n);

        var result = new ArrayList< LinkedList<Vector> >(n);
        for (int i = 0; i < n; i++) {
            result.add(new LinkedList<>());
        }

        // First step of perfect hashing
        for (var k :
                keys) {
            result.get(primary.calcHash(k))
                    .add(k);
        }

        return result;
    }

    /**
     * if buckets.size() > 1. Find
     * new universal hash function and
     * save new row to lookUpTable
     * @return hash function used to hash bucket
     */
    private Universal solveCollision(LinkedList<Vector> bucket, Universal prevHashFunc) {

        int nj = bucket.size();

        var rowHash = Util.emptyRowHashList((int) Math.pow(nj, 2));
        var newHashFunc = Util.randUHFunction((int) Math.pow(nj, 2));

        // looking for the hash function that can solve collision
        for (var key :
                bucket) {
            int hashIndex = newHashFunc.calcHash(key);
            // if collision
            if (rowHash.get(hashIndex) != null) {
                // choose another universal hash function
                return solveCollision(bucket, newHashFunc);
            } else {
                rowHash.set(hashIndex, key);
            }
        }

        // save new row to lookUpTable
        lookUpTable.table.add(new Table.Row(
                rowHash,
                newHashFunc
        ));

        return newHashFunc;
    }

    /**
     * Init lookUpTable. Final step of hashing
     * @return lookUpTable
     */
    private Table secondStep(List<LinkedList<Vector>> buckets) {

        lookUpTable = new Table();
        lookUpTable.table = new ArrayList<>(buckets.size());

        var currHashFunc = primary;

        for (var bucket :
                buckets) {
            int nj = bucket.size();
            if (nj == 0) {
                lookUpTable.table.add(null);
            } else if (nj == 1) {
                lookUpTable.table.add(
                        new Table.Row(
                                Arrays.asList(bucket.get(0)),
                                currHashFunc)
                );
            } else {
                currHashFunc = solveCollision(bucket, currHashFunc);
            }
        }

        return lookUpTable;
    }

    public class Table {

        /**
         * contains null
         */
        private List<Row> table;

        private static class Row {
            List<Vector> hashTable;
            Universal hashFunction;

            public Row(List<Vector> hashTable, Universal hashFunction) {
                this.hashTable = hashTable;
                this.hashFunction = hashFunction;
            }

            int getNumberHashedKeys() {
                return (int) Math.sqrt(hashTable.size());
            }

            // row number N with hashTable.size() > 1
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder(" - hash function: ").append(hashFunction);

                sb.append('\n');
                sb.append(" - memory to save : ");
                sb.append(hashTable.size()).append(" cells");
                sb.append('\n');
                sb.append(" - where saved data:");
                // example: index(3) = (23-8i) index(15) = ..
                for (int i = 0; i < hashTable.size(); i++) {
                    var key = hashTable.get(i);
                    if (key != null) {
                        sb.append("index(");
                        sb.append(i);
                        sb.append(") = ");
                        sb.append(key);
                    }
                }
                sb.append('\n');
                sb.append("------------------------------------------------------------------------\n");

                return sb.toString();
            }
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder("\t\t\t*** Look up table for ").append(table.size()).append(" keys ***\n");

            for (int i = 0; i < table.size(); i++) {
                sb.append("ROW #").append(i);
                var row = table.get(i);
                if (row == null || row.hashTable.size() == 0) {
                    sb.append(" - [empty]");
                } else if (row.hashTable.size() == 1) {
                    sb.append(" - ").append(row.hashTable.get(0));
                } else {
                    sb.append('\n');
                    sb.append(row);
                }
                sb.append('\n');
            }

            return sb.toString();
        }
    }
}
