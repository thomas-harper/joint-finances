import zio.Console.printLine
import zio.cli.HelpDoc.Span.text
import zio.cli.*

object App extends ZIOCliDefault:

  /**
  * First we define the commands of the Cli. To do that we need:
  *    - Create command options
  *    - Create command arguments
  *    - Create help (HelpDoc)
  */
  val options: Options[Boolean] = Options.boolean("local").alias("l")
  val arguments: Args[String] = Args.text("repository")
  val help: HelpDoc = HelpDoc.p("Creates a copy of an existing repository")

  val command: Command[(Boolean, String)] = Command("clone").subcommands(Command("clone", options, arguments).withHelp(help))

  val cliApp = CliApp.make(
    name = "Joint Contribution Calculator",
    version = "0.1",
    summary = text("Joint contribution calculator"),
    command = command
  ) {
    case _ => printLine("Test")
  }
