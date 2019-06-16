package com.daml.grpc

import com.digitalasset.ledger.api.v1.transaction_service
import com.digitalasset.ledger.api.v1.transaction_service.{
  GetTransactionsResponse,
  TransactionServiceGrpc
}
import com.digitalasset.ledger.api.{v1 => V1}
import io.grpc.ManagedChannelBuilder

object SimpleGrpcClient extends App {

  val channel = ManagedChannelBuilder.forAddress("localhost", 6865).usePlaintext(true).build
  private val ledgerId = "sandbox-fe2d1701-3197-430b-a14c-725b6c06efa7"
  val ledgerBegin = V1.ledger_offset.LedgerOffset(
    V1.ledger_offset.LedgerOffset.Value
      .Boundary(V1.ledger_offset.LedgerOffset.LedgerBoundary.LEDGER_BEGIN))

  // example getTransactionsRequest
  val aliceTransactionFilter =
    V1.transaction_filter.TransactionFilter(Map("Alice" -> V1.transaction_filter.Filters(None)))
  val bobTransactionFilter =
    V1.transaction_filter.TransactionFilter(Map("Bob" -> V1.transaction_filter.Filters(None)))

  val request = transaction_service.GetTransactionsRequest(ledgerId,
                                                           Option(ledgerBegin),
                                                           None,
                                                           Option(aliceTransactionFilter),
                                                           true,
                                                           None)

  //  new CommandCompletionServiceGrpc
  // service returns confirmation of completed commands but it is not possible to see initial raw command

  //stub for alternate command submission service to proxy to ethereum and back
  //  new CommandSubmissionService {
  //    override def submit(request: SubmitRequest): Future[Unit] = ???
  //  }

  //sync
  val blockingStub = TransactionServiceGrpc.blockingStub(channel)
  private val responses: Iterator[GetTransactionsResponse] = blockingStub.getTransactions(request)
  responses.map(reply => reply.transactions.map(transaction => transaction)).foreach(println)

}
