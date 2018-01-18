package cz.cuni.mff.linkeddatalinker;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

public class MainTest {

    private List<String> runLinkerAndGetOutput(
            final String file1,
            final String type1,
            final String path1,
            final String file2,
            final String type2,
            final String path2
    ) throws IOException {

        File tmp = File.createTempFile("prefix", "suffix");

        System.setOut(new PrintStream(tmp));

        Main.main(new String[] {
                getClass().getClassLoader().getResource(file1).getFile(),
                type1,
                path1,
                getClass().getClassLoader().getResource(file2).getFile(),
                type2,
                path2
        });

        return Files.readAllLines(Paths.get(tmp.toURI()));
    }

    @Test
    public void test1() throws IOException {
        List<String> output = runLinkerAndGetOutput(
                "test1/file1.in1",
                "<http://matfyz.cz/student>",
                "<http://matfyz.cz/ma_jmeno>",
                "test1/file2.in1",
                "<http://matfyz.cz/ucitel>",
                "<http://matfyz.cz/ma_jmeno>");

        assertThat(output, contains("<http://matfyz.cz/student/test1> <http://www.w3.org/2002/07/owl#sameAs> <http://matfyz.cz/ucitel/24> ."));
    }

    @Test
    public void test2() throws IOException {
        List<String> output = runLinkerAndGetOutput(
                "test2/file1.in2",
                "<http://matfyz.cz/student>",
                "<http://matfyz.cz/ma_jmeno>",
                "test2/file2.in2",
                "<http://matfyz.cz/ucitel>",
                "<http://matfyz.cz/ma_zaznam>|<http://matfyz.cz/ma_info>|<http://matfyz.cz/jmeno>");

        assertThat(output, contains("<http://matfyz.cz/student/test1> <http://www.w3.org/2002/07/owl#sameAs> <http://matfyz.cz/ucitel/24> ."));

    }

    @Test
    public void testComments() throws IOException {
        List<String> output = runLinkerAndGetOutput(
                "test3/file1.in3",
                "<http://matfyz.cz/student>",
                "<http://matfyz.cz/ma_jmeno>",
                "test3/file2.in3",
                "<http://matfyz.cz/ucitel>",
                "<http://matfyz.cz/ma_jmeno>");

        assertThat(output, contains("<http://matfyz.cz/student/test1> <http://www.w3.org/2002/07/owl#sameAs> <http://matfyz.cz/ucitel/24> ."));

    }

}