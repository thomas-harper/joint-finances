import zio.Console.printLine
import zio.cli.HelpDoc.Span.text
import zio.cli.*
import Agent.*
import Calculator.*

object App extends ZIOCliDefault:

  /**
  * First we define the commands of the Cli. To do that we need:
  *    - Create command options
  *    - Create command arguments
  *    - Create help (HelpDoc)
  */
  val options =
    Options.decimal("tni") ++ Options.decimal("tytd") ++ Options.decimal("tfc") ++
    Options.decimal("pni") ++ Options.decimal("pytd") ++ Options.decimal("pfc") ++
      Options.decimal("exp")
  val help: HelpDoc = HelpDoc.p("Creates a copy of an existing repository")

  val command = Command("cc", options).withHelp(help)

  val cliApp = CliApp.make(
    name = "Joint Contribution Calculator",
    version = "0.1",
    summary = text("Joint contribution calculator"),
    command = command
  ) {
    case (tni, tytd, tfc, pni, pytd, pfc, exp) =>
      val t = Agent(netIncome = tni, ytdContribution = tytd, fixedContribution = tfc)
      val p = Agent(netIncome = pni, ytdContribution = pytd, fixedContribution = pfc)
      val (ct, cp) = calculate(t, p, exp)
      printLine(f"T contributes $ct%,.2f - \nP Contributes $cp%,.2f -")
  }
