package eu.woodhouse

import zio.Console.printLine
import zio.cli.HelpDoc.Span.text
import zio.cli.*
import Calculator.*

object App extends ZIOCliDefault:

  /**
  * First we define the commands of the Cli. To do that we need:
  *    - Create command options
  *    - Create command arguments
  *    - Create help (HelpDoc)
  */
  val options =
    Options.decimal("ti") ++ Options.decimal("tytd") ++ Options.decimal("tfc") ++
    Options.decimal("pi") ++ Options.decimal("pytd") ++ Options.decimal("pfc") ++
      Options.decimal("exp") ++ Options.boolean("netto")
  val help: HelpDoc = HelpDoc.p("Calculates monthly contributions")

  val command = Command("joint-finances", options).withHelp(help)

  val cliApp = CliApp.make(
    name = "Joint Contribution Calculator",
    version = "0.1",
    summary = text("Joint contribution calculator"),
    command = command
  ) {
    case (ti, tytd, tfc, pi, pytd, pfc, exp, netto) =>
      val tni = if netto then ti else ti - incomeTax(ti)
      val pni = if netto then pi else pi - incomeTax(pi)
      val t = Agent(netIncome = tni, ytdContribution = tytd, fixedContribution = tfc)
      val p = Agent(netIncome = pni, ytdContribution = pytd, fixedContribution = pfc)
      val (ct, cp) = calculate(t, p, exp)
      printLine(f"T contributes $ct%,.2f - \nP Contributes $cp%,.2f -")
  }
