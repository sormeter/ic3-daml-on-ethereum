package com.daml.ethereum

import java.math.BigDecimal

import com.daml.ethereum.EthPublisher.web3
import org.slf4j.LoggerFactory
import org.web3j.crypto.{Credentials, WalletUtils}
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.utils.Convert

object EthPublisher extends App {
  private val logger = LoggerFactory.getLogger(this.getClass)

  private val web3: Web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"))
  //    Web3j.build(new HttpService("https://mainnet.infura.io/v3/533d2271072844c38be956e2093d8577"))

  val credentials = WalletUtils.loadCredentials(
    "ic3",
    "/Users/brianhealey/ic3-daml-on-ethereum/network/node0/keystore/UTC--2019-06-14T22-02-01.777877000Z--1f3d48d9ed26b528f5bbb72b36509b3400493ed7")

  val ethPublisher = new EthPublisher(web3, credentials)
  ethPublisher.transfer("0xe96beb2cbabf3fe4866a5885f863c736b8f7a0a7",
                        BigDecimal.valueOf(1.0),
                        Convert.Unit.GWEI)
  logger.info("client version" + ethPublisher.getClientVersion)
}

class EthPublisher(web3j: Web3j, credentials: Credentials) {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def transfer(toAddress: String, value: BigDecimal, unit: Convert.Unit): Unit = {
    val transactionReceipt = Transfer.sendFunds(web3, credentials, toAddress, value, unit).send()
    logger.info(s"Transaction receipt: $transactionReceipt")
  }

  def getClientVersion: String = {
    val web3ClientVersion = web3.web3ClientVersion().send()
    web3ClientVersion.getWeb3ClientVersion
  }

  def sendTransaction() = {
    //    web3j.ethSendTransaction(new Transaction())
  }

  def ethCall() = {
    //  web3.ethCall()
  }

}
