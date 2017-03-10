package solvas.api;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.apache.commons.cli.*;
import org.apache.commons.cli.Options;
import org.asciidoctor.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Generates documentation from the API specification.
 *
 * @author Niko Strijbol
 */
public class Generator {

    private static final String MARKDOWN = "md";
    private static final String ASCII_DOC = "adoc";
    private static final String HTML = "html";
    private static final String PDF = "pdf";

    public static void main(String[] args) throws IOException {
        CommandLineParser parser = new DefaultParser();
        try {
            Options options = defineArguments();

            // Try first for help command.
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("h")) {
                printHelp(options);
                return;
            }

            if (line.getOptionValue("i") == null || line.getOptionValue("o") == null) {
                printHelp(options);
                return;
            }

            String format = line.getOptionValue("format", HTML);
            MarkupLanguage language = fromString(format);

            String input = line.getOptionValue("i");
            Path inputPath = Paths.get(input);

            String output = line.getOptionValue("o");
            Path outputPath = Paths.get(output);

            Swagger2MarkupConfigBuilder config = new Swagger2MarkupConfigBuilder()
                    .withMarkupLanguage(language)
                    .withPathsGroupedBy(GroupBy.REGEX)
                    .withHeaderRegex("\\/(\\w+)(\\/.*|\\w)*$");

            if (line.hasOption("e")) {
                config.withGeneratedExamples();
            }

            Swagger2MarkupConverter.from(inputPath)
                    .withConfig(config.build())
                    .build()
                    .toFile(outputPath);

            // Generate HTML or PDF from ascii doc if needed
            if (format.equals(HTML) || format.equals(PDF)) {
                Asciidoctor doctor = Asciidoctor.Factory.create();
                Path fullOutput =  outputPath.resolveSibling(outputPath.getFileName() + ".adoc");
                File file = fullOutput.toFile();
                AttributesBuilder attributesBuilder = AttributesBuilder.attributes()
                        .tableOfContents(Placement.LEFT)
                        .tableOfContents(true)
                        .attribute("toclevels", 3);
                OptionsBuilder builder = OptionsBuilder.options()
                        .safe(SafeMode.SAFE)
                        .attributes(attributesBuilder)
                        .inPlace(true);
                if (format.equals(PDF)) {
                    System.out.println("Warning: converting to PDF is in alpha.");
                    builder.backend("pdf");
                }
                doctor.convertFile(file, builder);
                System.out.println("Can currently not delete intermediary .adoc file, due to a bug in Asciidoctor.");
                // See https://github.com/asciidoctor/asciidoctor/issues/1897
                // Files.delete(fullOuput);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("generator", options, true);
    }

    /**
     * Define the command line arguments.
     */
    private static Options defineArguments() {
        Options options = new Options();

        Option input = Option.builder("i")
                .longOpt("input")
                .argName("spec.yaml")
                .hasArg()
                .desc("The API spec file.")
                .build();

        Option output = Option.builder("o")
                .longOpt("output")
                .argName("output")
                .hasArg()
                .desc("The output file (without extension)")
                .build();

        Option examples = Option.builder("e")
                .longOpt("examples")
                .desc("Generate examples")
                .build();

        Option format = Option.builder("f")
                .longOpt("format")
                .argName(String.format("%s|%s|%s|%s", MARKDOWN, ASCII_DOC, HTML, PDF))
                .hasArg()
                .desc(String.format(
                        "Output format. Can be %s for Markdown, %s for Asciidoc, %s for HTML or %s for PDF",
                        MARKDOWN,
                        ASCII_DOC,
                        HTML,
                        PDF
                ))
                .build();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Display help and usage")
                .build();

        return options.addOption(input)
                .addOption(output)
                .addOption(format)
                .addOption(examples)
                .addOption(help);
    }

    private static MarkupLanguage fromString(String string) {
        switch (string) {
            case MARKDOWN:
                return MarkupLanguage.MARKDOWN;
            case HTML:
            case PDF:
            case ASCII_DOC:
                return MarkupLanguage.ASCIIDOC;
            default:
                throw new IllegalArgumentException("Unknown output format.");
        }
    }
}