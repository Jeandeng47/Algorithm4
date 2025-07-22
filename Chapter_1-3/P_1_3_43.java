import java.io.File;

import edu.princeton.cs.algs4.Queue;

public class P_1_3_43 {
    private static class DirEntry {
        File file;
        int level;

        DirEntry(File file, int level) {
            this.file = file;
            this.level = level;
        }   
    }

    // Helper method to traverse the directory structure recursively
    public static void traverse(File root) {
        if (root == null || !root.isDirectory() || !root.exists() ) {
            System.err.println("Error: " + root.getAbsolutePath() + " is not a valid directory.");
            return;
        }

        // Enqueue the root
        Queue<DirEntry> queue = new Queue<>();
        queue.enqueue(new DirEntry(root, 0));

        // Traverse the directory structure using BFS
        while (!queue.isEmpty()) {
            DirEntry current = queue.dequeue();
            int level = current.level;
            File file = current.file;

            // print level and name of file
            for (int i = 0; i < level; i++) {
                System.out.print("---");
            }
            System.out.println(file.getName());

            // if current is dir, go to next level
            if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                for (File child : childFiles) {
                    queue.enqueue(new DirEntry(child, level + 1));
                }
            }

        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java P_1_3_43 <directory>");
            System.exit(1);
        }

        File root = new File(args[0]);
        traverse(root);
    }
}
