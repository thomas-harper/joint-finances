package eu.woodhouse

import eu.woodhouse.Agent

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
