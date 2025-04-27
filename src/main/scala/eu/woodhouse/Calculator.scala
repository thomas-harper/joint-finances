package eu.woodhouse
import scala.math.*

import scala.math.BigDecimal.RoundingMode

object TaxConstants:
  val PBB: BigDecimal = 58800
  val municipalTaxRate = 0.306
  val stateTaxRate = 0.20
  val funeralTaxRate = 0.0007
  val stateTaxThreshold: BigDecimal = 625800
  val earnedIncomeTaxDeduction: BigDecimal = 1500
  val basicDeduction: BigDecimal = (0.293 * PBB / 100).setScale(0, RoundingMode.CEILING) * 100
  val tvFee: BigDecimal = (0.01 * 1.55 * PBB).setScale(0, RoundingMode.FLOOR)

object Calculator:
  private def agentContribution(agent: Agent, toBePaid: BigDecimal, totalYtdContribution: BigDecimal, goalRatio: BigDecimal): BigDecimal =
    ((totalYtdContribution + toBePaid) * goalRatio) - agent.ytdContribution + agent.fixedContribution

  def calculate(agentT: Agent, agentP: Agent, toBePaid: BigDecimal): (BigDecimal, BigDecimal) =
    val totalNetIncome = agentT.netIncome + agentP.netIncome
    val totalYtdContribution = agentT.ytdContribution + agentP.ytdContribution
    val toBePaidVariable = toBePaid - agentT.fixedContribution - agentP.fixedContribution
    val goalRatioT = agentT.netIncome/totalNetIncome
    val goalRatioP = agentP.netIncome/totalNetIncome
    val agentTContribution = agentContribution(agentT, toBePaidVariable, totalYtdContribution, goalRatioT)
    val agentPContribution = agentContribution(agentP, toBePaidVariable, totalYtdContribution, goalRatioP)
    (agentTContribution, agentPContribution)

  def incomeTax(income: BigDecimal): BigDecimal =
    import TaxConstants.*
    // Taxable Income Calculation
    val roundedIncome: BigDecimal = (income / 100).setScale(0, RoundingMode.FLOOR) * 100
    val taxableIncome = (roundedIncome - basicDeduction).setScale(0, RoundingMode.FLOOR)
    // Tax Calculation
    val municipalTax = (taxableIncome * municipalTaxRate).setScale(0, RoundingMode.FLOOR)
    val stateTax = ((taxableIncome - stateTaxThreshold) * stateTaxRate).setScale(0, RoundingMode.FLOOR)
    val funeralTax = (taxableIncome * funeralTaxRate).setScale(0, RoundingMode.FLOOR)
    // Tax Deductions
    val salaryTaxDeduction = (2.776 * PBB - basicDeduction) * (municipalTaxRate - funeralTaxRate)
    val totalTax = municipalTax + stateTax + funeralTax + tvFee
    val totalDeduction = salaryTaxDeduction + earnedIncomeTaxDeduction

    totalTax - totalDeduction

